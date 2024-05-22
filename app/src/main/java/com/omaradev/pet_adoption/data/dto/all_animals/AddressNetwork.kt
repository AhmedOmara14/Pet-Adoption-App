package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Address
import kotlinx.serialization.Serializable

@Serializable
data class AddressNetwork(
    val address1: String? = null,
    val address2: String? = null,
    val city: String? = null,
    val country: String? = null,
    val postcode: String? = null,
    val state: String? = null
)

fun AddressNetwork.toAddress(): Address {
    return Address(
        address1,
        address2,
        city,
        country,
        postcode,
        state,
    )
}
