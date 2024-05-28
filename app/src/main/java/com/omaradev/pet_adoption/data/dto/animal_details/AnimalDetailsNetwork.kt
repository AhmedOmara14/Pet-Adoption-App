package com.omaradev.pet_adoption.data.dto.animal_details

import com.omaradev.pet_adoption.data.dto.all_animals.AnimalNetwork
import com.omaradev.pet_adoption.domain.models.Animal
import kotlinx.serialization.Serializable

@Serializable
class AnimalDetailsNetwork {
    var animal: AnimalNetwork? = null
}