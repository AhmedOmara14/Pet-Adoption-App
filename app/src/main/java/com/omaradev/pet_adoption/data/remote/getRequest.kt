package com.omaradev.pet_adoption.data.remote


import com.omaradev.pet_adoption.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

suspend fun getRequest(
    endpoint: String,
    inputs: HashMap<String, Any>?,
    client: HttpClient,
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
            url {
                if (isPath.not()) inputs?.forEach { parameter(it.key, it.value) }
                else inputs?.forEach { "/${it.value}" }
            }
        }
    } catch (e: Exception) {
        throw e
    }
}