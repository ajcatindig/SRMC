package com.example.srmc.core.preference

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface PreferenceManager {
    val uiModeFlow : Flow<Boolean>
    suspend fun setDarkMode(enable : Boolean)
}