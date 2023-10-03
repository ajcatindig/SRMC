package com.example.srmc.composeapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.utils.connectivityManager
import com.example.srmc.utils.currentConnectivityState
import com.example.srmc.utils.observeConnectivityAsFlow

@Composable
fun currentConnectionState(): ConnectionState
{
    val connectivityManager = LocalContext.current.connectivityManager
    return remember { connectivityManager.currentConnectivityState }
}

@Composable
fun connectivityState(): State<ConnectionState>
{
    val connectivityManager = LocalContext.current.connectivityManager
    return produceState(initialValue = connectivityManager.currentConnectivityState) {
        connectivityManager.observeConnectivityAsFlow().collect { value = it }
    }
}