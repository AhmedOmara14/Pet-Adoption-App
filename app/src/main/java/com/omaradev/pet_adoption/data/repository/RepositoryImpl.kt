package com.omaradev.pet_adoption.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omaradev.pet_adoption.data.dto.JsonConfig
import com.omaradev.pet_adoption.data.dto.all_animals.AllAnimalsNetwork
import com.omaradev.pet_adoption.data.dto.all_animals.toAllAnimals
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenResponse
import com.omaradev.pet_adoption.data.remote.ApiService
import com.omaradev.pet_adoption.domain.models.AllAnimals
import com.omaradev.pet_adoption.domain.models.Animal
import com.omaradev.pet_adoption.domain.pagination.AnimalsPagingSource
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.domain.repository.Repository
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {
    override suspend fun requestToken(requestTokenBody: RequestTokenBody): Flow<RemoteRequestStatus<RequestTokenResponse>> {
        return executeRequest(requestTokenBody) { responseBody ->
            JsonConfig.json.decodeFromString(responseBody)
        }
    }

    override fun getAllAnimals(type: String, page: Int): Flow<PagingData<Animal>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { AnimalsPagingSource(apiService) }
        ).flow
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
