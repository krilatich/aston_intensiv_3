package ru.dima.aston_intensiv_3.presentation.edit_contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import ru.dima.aston_intensiv_3.databinding.FragmentEditContactBinding

class EditContactFragment : Fragment() {

    private var _binding: FragmentEditContactBinding? = null
    private val binding get() = _binding!!
    private var contactId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("chosen_contact") { _, bundle ->
            contactId = bundle.getString("id")
            with(binding) {
                etFirstName.setText(bundle.getString("first_name"))
                etLastName.setText(bundle.getString("last_name"))
                etPhoneNumber.setText(bundle.getString("phone_number"))
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditContactBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            setFragmentResult(
                "new_contact", bundleOf(
                    "id" to contactId,
                    "first_name" to binding.etFirstName.text.toString(),
                    "last_name" to binding.etLastName.text.toString(),
                    "phone_number" to binding.etPhoneNumber.text.toString(),
                )
            )

            val action =
                EditContactFragmentDirections
                    .actionEditContactFragmentToContactsFragment()
            view.findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}