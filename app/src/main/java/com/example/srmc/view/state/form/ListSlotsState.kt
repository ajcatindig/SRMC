package com.example.srmc.view.state.form

import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Slots
import com.example.srmc.view.state.State

data class ListSlotsState(
        val isLoading : Boolean = false ,
        val data : List<Slots> = emptyList() ,
        val error : String? = null ,
        val isConnectivityAvailable : Boolean? = null
): State
