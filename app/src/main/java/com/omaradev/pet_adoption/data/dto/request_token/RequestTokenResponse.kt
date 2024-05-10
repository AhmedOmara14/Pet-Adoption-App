package com.omaradev.pet_adoption.data.dto.request_token

import kotlinx.serialization.Serializable

@Serializable
class RequestTokenResponse {
    var token_type: String?=null
    var expires_in: Int?=null
    var access_token: String?=null
}
