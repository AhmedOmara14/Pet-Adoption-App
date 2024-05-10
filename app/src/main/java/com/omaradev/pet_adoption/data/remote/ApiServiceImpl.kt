package com.omaradev.pet_adoption.data.remote

import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun requestToken(requestTokenBody: RequestTokenBody): HttpResponse = postRequest("oauth2/token", requestTokenBody, client)

}