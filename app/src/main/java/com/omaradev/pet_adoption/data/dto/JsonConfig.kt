package com.omaradev.pet_adoption.data.dto

import kotlinx.serialization.json.Json

object JsonConfig {
    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }
}