package ru.dima.aston_intensiv_3.data.models

data class Contact(
    val id: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    var isSelected: Boolean = false
)