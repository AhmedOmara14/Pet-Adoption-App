package com.omaradev.pet_adoption.data.remote

import com.omaradev.pet_adoption.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

suspend fun postRequest(endpoint: String, requestBody: Any, client: HttpClient): HttpResponse {
    return try {
        val baseUrl = "${BuildConfig.BASE_URL}${BuildConfig.API_URL}"
        client.post(baseUrl + endpoint) {
            header("content-type", "application/json")
            setBody(requestBody)
        }
    } catch (e: Exception) {
        throw e
    }
}