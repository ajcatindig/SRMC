package com.example.srmc.view.state.main

import com.example.srmc.view.state.State

data class HomeState(
        val isLoading : Boolean = false,
        val error : String? = null,
        val isConnectivityAvailable: Boolean? = null) : State
