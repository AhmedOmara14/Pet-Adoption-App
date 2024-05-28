package com.omaradev.pet_adoption.domain.repository

import androidx.paging.PagingData
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenResponse
import com.omaradev.pet_adoption.domain.models.AllAnimals
import com.omaradev.pet_adoption.domain.models.Animal
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun requestToken(requestTokenBody: RequestTokenBody): Flow<RemoteRequestStatus<RequestTokenResponse>>
    fun getAllAnimals(
        type: String, page: Int
    ): Flow<PagingData<Animal>>

    fun getAnimalDetails(
        animalId: Int
    ): Flow<RemoteRequestStatus<Animal>>

}