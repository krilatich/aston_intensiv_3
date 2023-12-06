package ru.dima.aston_intensiv_3.presentation.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ru.dima.aston_intensiv_3.data.ContactsDB
import ru.dima.aston_intensiv_3.data.models.Contact
import ru.dima.aston_intensiv_3.databinding.FragmentContactsBinding
import ru.dima.aston_intensiv_3.presentation.contacts.adapter.ContactsAdapter


class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactsViewModel by viewModels()
    private var contactsAdapter: ContactsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupClickListeners()
        setupObservers()
    }

    private fun setupAdapter() {
        binding.contactsRecycler.adapter =
            ContactsAdapter {
                setFragmentResult(
                    "chosen_contact",
                    bundleOf(
                        "id" to it.id,
                        "first_name" to it.firstName,
                        "last_name" to it.lastName,
                        "phone_number" to it.phone,
                    )
                )
                val action =
                    ContactsFragmentDirections
                        .actionContactsFragmentToEditContactFragment()
                view?.findNavController()?.navigate(action)
            }
        contactsAdapter = binding.contactsRecycler.adapter as ContactsAdapter
        contactsAdapter?.submitList(ContactsDB.contacts.toList())
    }

    private fun setupClickListeners() {
        with(binding) {

            addContactBtn.setOnClickListener {
                val action =
                    ContactsFragmentDirections
                        .actionContactsFragmentToEditContactFragment()
                view?.findNavController()?.navigate(action)
            }

            deleteButton.setOnClickListener {
                viewModel.setContactsMode(ContactsMode.Delete)
            }

            deleteCancelButton.setOnClickListener {
                uncheckContacts()
                viewModel.setContactsMode(ContactsMode.Edit)
            }

            deleteConfirmButton.setOnClickListener {
                deleteSelectedContacts()
                viewModel.setContactsMode(ContactsMode.Edit)
            }
        }
    }

    private fun setupObservers() {
        viewModel.contactsMode.observe(viewLifecycleOwner) {
            contactsAdapter?.setSelectionMode(it)
            when (it) {
                ContactsMode.Delete -> {
                    with(binding) {
                        addContactBtn.visibility = View.GONE
                        deleteModeButtons.visibility = View.VISIBLE
                    }
                }

                ContactsMode.Edit -> {
                    with(binding) {
                        addContactBtn.visibility = View.VISIBLE
                        deleteModeButtons.visibility = View.GONE
                    }
                }

                else -> {}
            }
        }
    }

    private fun deleteSelectedContacts() {

        val newList = mutableListOf<Contact>()
        ContactsDB.contacts.forEach {
            if (!it.isSelected) newList.add(it)
        }
        ContactsDB.contacts = newList
        contactsAdapter?.submitList(newList)
    }

    private fun uncheckContacts() {

        ContactsDB.contacts = ContactsDB.contacts.map {
            it.copy(isSelected = false)
        }.toMutableList()

        contactsAdapter?.submitList(ContactsDB.contacts.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("new_contact") { _, bundle ->
            val contactId = bundle.getString("id")
            val newContact = Contact(
                id = contactId ?: (ContactsDB.contacts.size + 1).toString(),
                firstName = bundle.getString("first_name") ?: "NULL",
                lastName = bundle.getString("last_name") ?: "NULL",
                phone = bundle.getString("phone_number") ?: "NULL"
            )

            if (contactId != null) {
                ContactsDB.contacts = ContactsDB.contacts.map {
                    if (it.id == contactId) newContact else it
                }.toMutableList()
            } else {
                ContactsDB.contacts.add(newContact)
            }
            contactsAdapter?.submitList(ContactsDB.contacts.toList())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}