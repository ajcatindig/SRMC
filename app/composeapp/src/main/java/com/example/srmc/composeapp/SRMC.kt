package com.example.srmc.composeapp

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SRMC : Application() , Configuration.Provider
{
    @Inject
    lateinit var workerFactory : HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
            Configuration.Builder()
                    .setWorkerFactory(workerFactory)
                    .setMinimumLoggingLevel(Log.DEBUG)
                    .build()
}