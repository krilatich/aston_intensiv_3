package ru.dima.aston_intensiv_3.data

import ru.dima.aston_intensiv_3.data.models.Contact

object ContactsDB {
    var contacts = MutableList(100){
        Contact(
            id = it.toString(),
            firstName = "Имя$it",
            lastName = "Фамилия$it",
            phone = "+ 7 (999) 999-99-99"
        )
    }
}