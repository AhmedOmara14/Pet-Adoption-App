package com.omaradev.pet_adoption.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import com.omaradev.pet_adoption.main.viewmodel.MainViewModel
import com.omaradev.pet_adoption.ui.NavigationComposeShared
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComposeShared(isSystemInDarkTheme())
        }
    }
}

