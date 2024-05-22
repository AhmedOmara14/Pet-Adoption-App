package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Breeds
import com.omaradev.pet_adoption.domain.models.Colors
import kotlinx.serialization.Serializable

@Serializable
class BreedsNetwork {
    var mixed: Boolean? = null
    var primary: String? = null
    var secondary: String? = null
    var unknown: Boolean? = null
}

fun BreedsNetwork.toBreeds(): Breeds {
    return Breeds(
        mixed,
        primary,
        secondary,
        unknown
    )
}