package com.example.srmc.core.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    /**
     * Gives the realtime updates of a [ConnectionState]
     */
    val connectionState : Flow<ConnectionState>
    /**
     * Retrieves the current [ConnectionState]
     */
    val currentConnectionState : ConnectionState
}