package com.omaradev.pet_adoption.data.di

import com.omaradev.pet_adoption.data.remote.ApiService
import com.omaradev.pet_adoption.data.repository.RepositoryImpl
import com.omaradev.pet_adoption.domain.repository.Repository
import org.koin.dsl.module

object RepositoryModule {
    val RepositoryModule = module {
        single<Repository> { provideRepositoryImpl(get()) }
    }

    private fun provideRepositoryImpl(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }
}