package com.omaradev.pet_adoption.data.remote

import android.content.Context
import android.preference.PreferenceManager
import androidx.activity.compose.LocalActivityResultRegistryOwner.current
import com.omaradev.pet_adoption.BuildConfig
import com.omaradev.pet_adoption.ui.main.AppBase
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

suspend fun getRequest(
    endpoint: String, inputs: HashMap<String, Any>?, client: HttpClient,requestTokenValue:String
): HttpResponse {
    val url = "${BuildConfig.BASE_URL}${BuildConfig.API_URL}$endpoint"
    return try {
        client.get(url) {
            header("content-type", "application/json")
            header("Authorization", "Bearer $requestTokenValue")
            inputs?.map {
                parameter(it.key, it.value)
            }
        }
    } catch (e: Exception) {
        println("Exception occurred: $e")
        throw e
    }
}