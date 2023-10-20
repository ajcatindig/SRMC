package com.example.srmc.view.state.detail

import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules
import com.example.srmc.view.state.State

data class DoctorDetailState(
        val isLoading : Boolean = false ,
        val data : Doctor,
        val error : String? = null ,
        val isConnectivityAvailable : Boolean? = null
) : State
