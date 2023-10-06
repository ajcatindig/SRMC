package com.example.srmc.view.state.auth

import com.example.srmc.view.state.State
data class LoginState(
        val isLoading : Boolean = false,
        val isLoggedIn : Boolean = false,
        val error : String? = null,
        val email : String = "",
        val password : String = ""
) : State
