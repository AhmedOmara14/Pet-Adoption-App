package com.omaradev.pet_adoption.data.remote

import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import io.ktor.client.statement.HttpResponse

interface ApiService {
    suspend fun requestToken(requestTokenBody: RequestTokenBody): HttpResponse
}