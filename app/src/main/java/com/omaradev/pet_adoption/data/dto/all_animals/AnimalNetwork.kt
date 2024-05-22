package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Animal
import kotlinx.serialization.Serializable

@Serializable
class AnimalNetwork {
    var age: String? = null
    var attributes: AttributesNetwork? = null
    var breeds: BreedsNetwork? = null
    var coat: String? = null
    var colors: ColorsNetwork? = null
    var contact: ContactNetwork? = null
    var description: String? = null
    var distance: Double? = null
    var gender: String? = null
    var id: Int? = null
    var name: String? = null
    var photos: List<PhotoNetwork>? = null
    var size: String? = null
    var status: String? = null
    var published_at: String? = null
    var tags: List<String>? = null
    var type: String? = null

    var _links: Links? = null
    var environment: Environment? = null
    var species: String? = null
    var videos: List<Video>? = null
    var organization_id: String? = null
    var organization_animal_id: String? = null
    var primary_photo_cropped: PhotoNetwork? = null
    var url: String? = null
    var status_changed_at: String? = null
}

fun AnimalNetwork.toAnimal(): Animal {
    return Animal(
        age,
        attributes?.toAttributes(),
        breeds?.toBreeds(),
        coat,
        colors?.toColor(),
        contact?.toContact(),
        description,
        distance ?: 0.0,
        gender,
        id ?: 0,
        name,
        photos?.map { it.toPhoto() },
        size,
        status,
        published_at,
        tags,
        type,
    )
}