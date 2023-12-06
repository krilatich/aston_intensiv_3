package ru.dima.aston_intensiv_3.presentation.contacts.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.dima.aston_intensiv_3.data.models.Contact
import ru.dima.aston_intensiv_3.databinding.ItemContactBinding
import ru.dima.aston_intensiv_3.presentation.contacts.ContactsMode


class ContactsAdapter(
    private val onClickAction: (contact: Contact) -> Unit,
) : ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(ContactsDiffUtil) {

    private var contactsMode: ContactsMode = ContactsMode.Edit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater)
        val viewHolder = ContactViewHolder(binding)

        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)

            when (contactsMode) {
                ContactsMode.Delete -> {
                    item.isSelected = !item.isSelected
                    viewHolder.itemView.setBackgroundColor(if (item.isSelected) Color.CYAN else Color.WHITE)
                }

                ContactsMode.Edit -> onClickAction(item)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    class ContactViewHolder(private val binding: ItemContactBinding) : ViewHolder(binding.root) {

        fun bind(model: Contact) {
            with(binding) {
                "${model.firstName} ${model.lastName}".also { tvName.text = it }
                tvPhoneNumber.text = model.phone
                root
                    .setBackgroundColor(if (model.isSelected) Color.CYAN else Color.WHITE)
            }
        }
    }

    fun setSelectionMode(mode: ContactsMode) {
        contactsMode = mode
    }
}

