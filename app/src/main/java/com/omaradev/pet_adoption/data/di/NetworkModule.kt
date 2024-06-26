package com.omaradev.pet_adoption.data.di
import android.preference.PreferenceManager
import android.util.Log
import com.omaradev.pet_adoption.BuildConfig
import com.omaradev.pet_adoption.ui.main.AppBase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

object NetworkModule {
    private fun getRequestToken(): String? {
        val context = AppBase.appContext
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("token", null)
    }

    val NetworkModule = module {
        single<HttpClient> { provideKtorClient() }
    }

    private fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP Client", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true

                })
            }
            defaultRequest {
                headers {
                    append("content-type", "application/json")
                }
                getRequestToken()?.let { bearerAuth(it) }
            }
        }
    }
}
