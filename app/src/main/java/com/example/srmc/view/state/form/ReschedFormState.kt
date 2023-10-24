package com.example.srmc.view.state.form

import com.example.srmc.view.state.State

data class ReschedFormState(
        val isLoading : Boolean = false,
        val isSuccess : String? = null,
        val error :  String? = null,
        val id : Int = 0,
        val date : String = "",
        val time : String = ""
) : State
