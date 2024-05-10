package com.omaradev.pet_adoption.data.repository

import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenResponse
import com.omaradev.pet_adoption.data.remote.ApiService
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.domain.repository.Repository
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class RepositoryImpl(
    var apiService: ApiService
) : Repository {

    override suspend fun requestToken(requestTokenBody: RequestTokenBody): Flow<RemoteRequestStatus<RequestTokenResponse>> {
        return executeRequest(requestTokenBody) { responseBody ->
            Json.decodeFromString(responseBody)
        }
    }

    private suspend fun <T> executeRequest(
        requestTokenBody: RequestTokenBody,
        responseMapper: (String) -> T
    ): Flow<RemoteRequestStatus<T>> {
        return flow {
            try {
                val response = apiService.requestToken(requestTokenBody)
                if (response.status.isSuccess()) {
                    val responseBody = response.body<String>()
                    val mappedResponse = responseMapper(responseBody)
                    emit(
                        RemoteRequestStatus.OnSuccessRequest(
                            responseBody = mappedResponse,
                        )
                    )
                } else emit(RemoteRequestStatus.OnFailedRequest(null, "Request failed"))

            } catch (e: Exception) {
                emit(
                    RemoteRequestStatus.OnFailedRequest(
                        null,
                        e.localizedMessage ?: "Something went wrong"
                    )
                )
            }
        }
    }
}
