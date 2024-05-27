package com.omaradev.pet_adoption.ui.home.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omaradev.pet_adoption.domain.models.AllAnimals
import com.omaradev.pet_adoption.domain.models.Animal
import com.omaradev.pet_adoption.domain.pagination.AnimalsPagingSource
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.koin.dsl.module

val homeViewModelModule = module {
    single<HomeViewModel> { HomeViewModel(get()) }
}

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _animalsItems =
        MutableStateFlow<RemoteRequestStatus<AllAnimals>>(
            RemoteRequestStatus.ToggleLoading(false)
        )
    val animalsItems: MutableStateFlow<RemoteRequestStatus<AllAnimals>> get() = _animalsItems


    // PagingData flow for animals, cached in viewModelScope

    val animals: Flow<PagingData<Animal>> =
        repository.getAllAnimals(type = "dog", page = 1).cachedIn(viewModelScope)


}