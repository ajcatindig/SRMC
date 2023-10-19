package com.example.srmc.view.state.detail

import com.example.srmc.core.model.Schedules
import com.example.srmc.view.state.State
import java.lang.Error

data class ListSchedState(
        val isLoading : Boolean = false ,
        val data : List<Schedules> = emptyList() ,
        val error : String? = null ,
        val isConnectivityAvailable : Boolean? = null
) : State