package com.example.srmc.view.state.detail

import com.example.srmc.core.model.Appointments
import com.example.srmc.view.state.State

data class AppointmentDetailState(
        val isLoading : Boolean = false,
        val data : Appointments,
        val error : String? = null,
        val isConnectivityAvailable : Boolean? = null
) : State
