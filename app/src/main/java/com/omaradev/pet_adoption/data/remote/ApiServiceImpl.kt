package com.omaradev.pet_adoption.data.remote

import android.preference.PreferenceManager
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.ui.main.AppBase
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {

    companion object {
        const val MAX_PER_PAGE = 10
    }

    private fun getRequestToken(): String? {
        val context = AppBase.appContext
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("token", null)
    }


    override suspend fun requestToken(requestTokenBody: RequestTokenBody): HttpResponse =
        postRequest("oauth2/token", requestTokenBody, client)

    override suspend fun getAllAnimals(type: String, page: Int): HttpResponse {
        val inputs = HashMap<String, Any>()
        inputs["type"] = type
        inputs["page"] = page
        inputs["limit"] = MAX_PER_PAGE

        return getRequest("animals", inputs, client, getRequestToken() ?: "")
    }
}