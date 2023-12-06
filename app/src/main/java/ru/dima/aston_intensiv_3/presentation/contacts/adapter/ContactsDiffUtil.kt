package ru.dima.aston_intensiv_3.presentation.contacts.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.dima.aston_intensiv_3.data.models.Contact

object ContactsDiffUtil : DiffUtil.ItemCallback<Contact>() {

    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }

}