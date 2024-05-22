package com.omaradev.pet_adoption.domain.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omaradev.pet_adoption.data.dto.JsonConfig
import com.omaradev.pet_adoption.data.dto.all_animals.AllAnimalsNetwork
import com.omaradev.pet_adoption.data.dto.all_animals.AnimalNetwork
import com.omaradev.pet_adoption.data.dto.all_animals.toAllAnimals
import com.omaradev.pet_adoption.data.dto.all_animals.toAnimal
import com.omaradev.pet_adoption.data.remote.ApiService
import com.omaradev.pet_adoption.domain.models.AllAnimals
import com.omaradev.pet_adoption.domain.models.Animal
import com.omaradev.pet_adoption.domain.repository.Repository
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import io.ktor.client.call.body
import io.ktor.http.isSuccess


class AnimalsPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Animal>() {
    companion object {
        const val STARTING_KEY = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Animal>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Animal> {
        val startKey = params.key ?: STARTING_KEY
        return try {
            val response = apiService.getAllAnimals("dog", startKey)
            if (response.status.isSuccess()) {
                val responseBody = response.body<String>()
                val mappedResponse =
                    JsonConfig.json.decodeFromString<AllAnimalsNetwork>(responseBody)

                val nextKey =
                    if (mappedResponse.pagination.current_page == mappedResponse.pagination?.total_pages) null else startKey + 1

                LoadResult.Page(
                    data = mappedResponse.animals.map { it.toAnimal() },
                    prevKey = if (startKey == STARTING_KEY) null else startKey - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(
                    Throwable("Request failed with status: ${response.status.value}")
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(
                Throwable(
                    e.localizedMessage ?: "Something went wrong"
                )
            )
        }
    }

}
