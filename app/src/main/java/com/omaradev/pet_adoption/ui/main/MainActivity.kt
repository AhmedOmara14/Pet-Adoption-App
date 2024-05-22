package com.omaradev.pet_adoption.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.lifecycleScope
import com.omaradev.pet_adoption.domain.repository.RemoteRequestStatus
import com.omaradev.pet_adoption.ui.main.viewmodel.MainViewModel
import com.omaradev.pet_adoption.ui.NavigationComposeShared
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeRequestTokenResponseItems()
    }

    private fun observeRequestTokenResponseItems() {
        lifecycleScope.launch {
            viewModel.requestTokenResponseItems.collectIndexed { _, responseStatus ->
                when (responseStatus) {
                    is RemoteRequestStatus.OnSuccessRequest -> {
                        val responseBody = responseStatus.responseBody
                        responseBody.access_token?.let {
                            viewModel.saveTokenToLocalPrefs(it, this@MainActivity)
                            setContent {
                                NavigationComposeShared(isSystemInDarkTheme())
                            }
                        }
                    }
                    // Handle other response statuses if necessary
                    else -> {}
                }
            }
        }
    }
}
