package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.AllAnimals
import kotlinx.serialization.Serializable

@Serializable
class AllAnimalsNetwork {
    lateinit var animals: List<AnimalNetwork>
    lateinit var pagination: PaginationNetwork
}

// Extension functions to convert between DTO and domain model
fun AllAnimalsNetwork.toAllAnimals(): AllAnimals {
    return AllAnimals(
        animals = animals?.map { it.toAnimal() }, pagination = pagination?.toPagination()
    )
}

