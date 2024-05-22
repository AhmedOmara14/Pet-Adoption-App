package com.omaradev.pet_adoption.data.dto.all_animals

import com.omaradev.pet_adoption.domain.models.Attributes
import kotlinx.serialization.Serializable

@Serializable
class AttributesNetwork {
    val declawed: Boolean? = null
    val house_trained: Boolean? = null
    val shots_current: Boolean? = null
    val spayed_neutered: Boolean? = null
    val special_needs: Boolean? = null
}

fun AttributesNetwork.toAttributes(): Attributes {
    return Attributes(
        declawed,
        house_trained,
        shots_current,
        spayed_neutered,
        special_needs,
    )
}