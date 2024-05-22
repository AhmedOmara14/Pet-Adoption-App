package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Address
import com.omaradev.pet_adoption.domain.models.Colors
import kotlinx.serialization.Serializable

@Serializable
class ColorsNetwork {
    val primary: String? = null
    val secondary: String? = null
    val tertiary: String? = null
}

fun ColorsNetwork.toColor(): Colors {
    return Colors(
        primary,
        secondary,
        tertiary
    )
}