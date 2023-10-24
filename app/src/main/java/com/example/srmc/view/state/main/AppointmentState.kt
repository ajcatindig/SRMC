package com.example.srmc.view.state.main

import com.example.srmc.core.model.Appointments
import com.example.srmc.view.state.State

data class AppointmentState(
        val isLoading : Boolean = false,
        val data : List<Appointments> = emptyList(),
        val error : String? = null,
        val isConnectivityAvailable: Boolean? = null) : State