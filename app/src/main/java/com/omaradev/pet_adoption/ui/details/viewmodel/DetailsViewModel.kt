package com.omaradev.pet_adoption.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omaradev.pet_adoption.domain.models.AllAnimals
import com.omaradev.pet_adoption.domain.models.Animal
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.dsl.module

val detailsViewModelModule = module {
    single { DetailsViewModel(get()) }
}

class DetailsViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _animalItem =
        MutableStateFlow<RemoteRequestStatus<Animal>>(
            RemoteRequestStatus.ToggleLoading(false)
        )
    val animalItem: MutableStateFlow<RemoteRequestStatus<Animal>> get() = _animalItem

    var animal: Animal? = null

    fun getAnimalDetails(animalId: Int) = viewModelScope.launch {
        repository.getAnimalDetails(animalId).collectLatest {
            _animalItem.value = it
        }
    }


}