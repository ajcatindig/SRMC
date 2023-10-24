package com.example.srmc.view.state.detail

import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Slots
import com.example.srmc.view.state.State

data class SchedDetailState(
        val isLoading : Boolean = false ,
        val data : Schedules ,
        val error : String? = null ,
        val isConnectivityAvailable : Boolean? = null
) : State
