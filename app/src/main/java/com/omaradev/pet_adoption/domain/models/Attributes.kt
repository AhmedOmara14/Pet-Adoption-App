package com.omaradev.pet_adoption.domain.models

import com.omaradev.pet_adoption.data.dto.all_animals.AnimalNetwork

data class Attributes(
    val declawed: Boolean? = null,
    val house_trained: Boolean? = null,
    val shots_current: Boolean? = null,
    val spayed_neutered: Boolean? = null,
    val special_needs: Boolean? = null,
)

