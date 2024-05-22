package com.omaradev.pet_adoption.ui.main.viewmodel

import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaradev.pet_adoption.BuildConfig
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenBody
import com.omaradev.pet_adoption.data.dto.request_token.RequestTokenResponse
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.domain.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.dsl.module

val mainViewModelModule = module {
    single<MainViewModel> { MainViewModel(get()) }
}

class MainViewModel(
    val repository: Repository
) : ViewModel() {

    init {
        requestToken(RequestTokenBody().apply {
            client_id = BuildConfig.client_id
            client_secret = BuildConfig.client_secret
            grant_type = BuildConfig.grant_type
        })
    }

    private var _requestTokenResponseItems =
        MutableStateFlow<RemoteRequestStatus<RequestTokenResponse>>(
            RemoteRequestStatus.ToggleLoading(false)
        )
    val requestTokenResponseItems: MutableStateFlow<RemoteRequestStatus<RequestTokenResponse>> get() = _requestTokenResponseItems

    private fun requestToken(requestTokenBody: RequestTokenBody) = viewModelScope.launch {
        repository.requestToken(requestTokenBody).collectLatest {
            _requestTokenResponseItems.value = it
        }
    }

    fun saveTokenToLocalPrefs(token: String, current: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(current)
        prefs.edit().putString("token", token).apply()
    }
}