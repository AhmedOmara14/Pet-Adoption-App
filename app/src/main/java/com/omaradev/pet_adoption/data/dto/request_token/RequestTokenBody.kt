package com.omaradev.pet_adoption.data.dto.request_token

import kotlinx.serialization.Serializable

@Serializable
class RequestTokenBody {
    var grant_type: String? = null
    var client_id: String? = null
    var client_secret: String? = null
}

