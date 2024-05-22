package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Address
import com.omaradev.pet_adoption.domain.models.Contact
import kotlinx.serialization.Serializable

@Serializable
class ContactNetwork {
    val address: AddressNetwork? = null
    val email: String? = null
    val phone: String? = null
}

fun ContactNetwork.toContact(): Contact {
    return Contact(
        address?.toAddress(),
        email,
        phone
    )
}