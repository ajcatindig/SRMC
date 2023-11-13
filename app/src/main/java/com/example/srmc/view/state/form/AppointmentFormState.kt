package com.example.srmc.view.state.form

import com.example.srmc.view.state.State

data class AppointmentFormState(
        val isLoading : Boolean = false,
        val isSuccess : String? = null,
        val error :  String? = null,
        val type : String = "",
        val doctor_id : Int = 0,
        val date : String = "",
        val time : String = ""
) : State