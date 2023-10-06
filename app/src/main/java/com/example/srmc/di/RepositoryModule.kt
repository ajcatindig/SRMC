package com.example.srmc.di

import com.example.srmc.core.repository.AuthRepository
import com.example.srmc.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun srmcAuthRepository(srmcAuthRepositoryImpl : AuthRepositoryImpl) : AuthRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository