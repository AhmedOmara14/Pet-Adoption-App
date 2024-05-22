package com.omaradev.pet_adoption.domain.models

data class Contact(
    val address: Address? = null,
    val email: String? = null,
    val phone: String? = null,
)