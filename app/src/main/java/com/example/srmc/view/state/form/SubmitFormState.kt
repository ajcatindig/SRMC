package com.example.srmc.view.state.form

import com.example.srmc.view.state.State

data class SubmitFormState(
        val isLoading : Boolean = false,
        val isSuccess : String? = null,
        val error : String? = null,
        val doctor_id : Int = 0,
        val scheduleDate : String = "",
        val scheduleSlot : String = ""
) : State
