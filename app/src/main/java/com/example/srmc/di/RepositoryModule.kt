package com.example.srmc.di

import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.core.repository.AuthRepository
import com.example.srmc.core.repository.DoctorRepository
import com.example.srmc.core.repository.UserRepository
import com.example.srmc.repository.AppointmentRepositoryImpl
import com.example.srmc.repository.AuthRepositoryImpl
import com.example.srmc.repository.DoctorRepositoryImpl
import com.example.srmc.repository.UserRepositoryImpl
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
    @RemoteRepository
    fun srmcDoctorRepository(srmcDoctorRepositoryImpl : DoctorRepositoryImpl) : DoctorRepository

    @Binds
    @RemoteRepository
    fun srmcUserRepository(srmcUserRepositoryImpl : UserRepositoryImpl) : UserRepository

    @Binds
    @RemoteRepository
    fun srmcAppointmentRepository(srmcAppointmentRepositoryImpl : AppointmentRepositoryImpl) : AppointmentRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository