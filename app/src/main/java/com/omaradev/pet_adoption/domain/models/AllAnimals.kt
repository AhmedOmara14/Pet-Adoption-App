package com.omaradev.pet_adoption.domain.models

data class AllAnimals(
    val animals: List<Animal>? = null,
    val pagination: Pagination? = null,
)
