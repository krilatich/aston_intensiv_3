package ru.dima.aston_intensiv_3.presentation.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel: ViewModel() {
    
    private val _contactsMode = MutableLiveData(ContactsMode.Edit)
        val contactsMode:LiveData<ContactsMode> = _contactsMode


    fun setContactsMode(mode: ContactsMode) {
        _contactsMode.value = mode
    }
}