package com.example.srmc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository