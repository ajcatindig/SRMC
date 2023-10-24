package com.example.srmc.view.state.form

import com.example.srmc.view.state.State

data class CancelState(
        val isLoading : Boolean = false,
        val isSuccess : String? = null,
        val error : String? = null,
        val id : Int = 0
) : State
