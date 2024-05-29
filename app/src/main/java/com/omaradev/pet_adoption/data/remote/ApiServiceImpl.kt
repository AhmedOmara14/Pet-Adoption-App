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
    override suspend fun requestToken(requestTokenBody: RequestTokenBody): HttpResponse =
        postRequest("oauth2/token", requestTokenBody, client)

    override suspend fun getAllAnimals(type: String, page: Int): HttpResponse {
        val inputs = HashMap<String, Any>()
        inputs["type"] = type
        inputs["page"] = page
        inputs["limit"] = MAX_PER_PAGE

        return getRequest("animals", inputs, client, false)
    }

    override suspend fun getDetailsOfAnimal(animalId: Int): HttpResponse {
        val inputs = HashMap<String, Any>()
        inputs["id"] = animalId
        return getRequest("animals", inputs, client, isPath = true)
    }
}