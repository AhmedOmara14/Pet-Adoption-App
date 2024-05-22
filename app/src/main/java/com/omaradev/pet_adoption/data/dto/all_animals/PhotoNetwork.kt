package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Photo
import kotlinx.serialization.Serializable

@Serializable
class PhotoNetwork {
    lateinit var full: String
    lateinit var large: String
    lateinit var medium: String
    lateinit var small: String
}

fun PhotoNetwork.toPhoto(): Photo {
    return Photo(
        full,
        large,
        medium,
        small
    )
}