package com.example.srmc.di

import com.example.srmc.core.repository.AuthRepository
import com.example.srmc.core.repository.DoctorRepository
import com.example.srmc.repository.AuthRepositoryImpl
import com.example.srmc.repository.DoctorRepositoryImpl
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

    @Binds
    fun srmcDoctorRepository(srmcDoctorRepositoryImpl : DoctorRepositoryImpl) : DoctorRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository