package com.omaradev.pet_adoption.domain.repository

import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun requestToken(requestTokenBody: RequestTokenBody): Flow<RemoteRequestStatus<RequestTokenResponse>>
}