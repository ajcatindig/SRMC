package com.example.srmc.view.state.main

import com.example.srmc.core.model.Doctor
import com.example.srmc.view.state.State

data class HomeState(
        val isLoading : Boolean = false ,
        val data : List<Doctor> = emptyList() ,
        val error : String? = null ,
        val isConnectivityAvailable: Boolean? = null) : State
