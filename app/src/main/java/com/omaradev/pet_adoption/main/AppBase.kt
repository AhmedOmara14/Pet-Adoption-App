package com.omaradev.pet_adoption.main

import android.app.Application
import com.omaradev.pet_adoption.data.di.ApiServiceModule
import com.omaradev.pet_adoption.data.di.NetworkModule
import com.omaradev.pet_adoption.data.di.RepositoryModule
import com.omaradev.pet_adoption.main.viewmodel.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppBase : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppBase)
            modules(
                ApiServiceModule.ApiServiceModule,
                RepositoryModule.RepositoryModule,
                NetworkModule.NetworkModule,
                mainViewModelModule
            )
        }
    }
}