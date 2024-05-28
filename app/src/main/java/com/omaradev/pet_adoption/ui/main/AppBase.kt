package com.omaradev.pet_adoption.ui.main

import android.app.Application
import com.omaradev.pet_adoption.data.di.ApiServiceModule
import com.omaradev.pet_adoption.data.di.NetworkModule
import com.omaradev.pet_adoption.data.di.RepositoryModule
import com.omaradev.pet_adoption.ui.details.viewmodel.detailsViewModelModule
import com.omaradev.pet_adoption.ui.home.viewmodel.homeViewModelModule
import com.omaradev.pet_adoption.ui.main.viewmodel.mainViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppBase : Application() {

    companion object {
        private var instance: AppBase? = null

        val appContext: Application
            get() = instance ?: throw IllegalStateException("Application instance is null")
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@AppBase)
            modules(
                ApiServiceModule.ApiServiceModule,
                RepositoryModule.RepositoryModule,
                NetworkModule.NetworkModule,
                mainViewModelModule,
                homeViewModelModule,
                detailsViewModelModule
            )
        }
    }
}
