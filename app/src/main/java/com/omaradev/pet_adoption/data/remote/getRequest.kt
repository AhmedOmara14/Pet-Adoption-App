package com.omaradev.pet_adoption.data.remote


import com.omaradev.pet_adoption.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

suspend fun getRequest(
    endpoint: String,
    inputs: HashMap<String, Any>?,
    client: HttpClient,
    requestTokenValue: String,
    isPath: Boolean = false
): HttpResponse {
    val baseUrl = "${BuildConfig.BASE_URL}${BuildConfig.API_URL}"
    val url = buildString {
        append(baseUrl)
        append(endpoint)
        if (isPath) inputs?.forEach { append("/${it.value}") }
    }

    return try {
        client.get(url) {
            header("Content-Type", "application/json")
            header("Authorization", "Bearer $requestTokenValue")
            if (isPath.not()) inputs?.forEach { parameter(it.key, it.value) }
        }
    } catch (e: Exception) {
        // Log the exception with proper logging library (not println)
        throw e
    }
}