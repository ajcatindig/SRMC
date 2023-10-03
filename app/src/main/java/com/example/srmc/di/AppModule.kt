package com.example.srmc.di

import android.app.Application
import androidx.work.WorkManager
import com.example.srmc.connectivity.ConnectivityObserverImpl
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.preference.PreferenceManager
import com.example.srmc.core.session.SessionManager
import com.example.srmc.preference.PreferenceManagerImpl
import com.example.srmc.preference.uiModePrefDataStore
import com.example.srmc.session.SRMCSharedPreferencesFactory
import com.example.srmc.session.SessionManagerImpl
import com.example.srmc.utils.connectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(application: Application): PreferenceManager
    {
        return PreferenceManagerImpl(application.uiModePrefDataStore)
    }

    @Singleton
    @Provides
    fun provideSessionManager(application: Application): SessionManager
    {
        return SessionManagerImpl(SRMCSharedPreferencesFactory.sessionPreferences(application))
    }

    @Singleton
    @Provides
    fun provideConnectivityObserver(application: Application): ConnectivityObserver
    {
        return ConnectivityObserverImpl(application.connectivityManager)
    }

    @Singleton
    @Provides
    fun provideWorkManager(application: Application): WorkManager
    {
        return WorkManager.getInstance(application)
    }
}