package com.example.srmc.view.state.main

import com.example.srmc.core.model.User
import com.example.srmc.view.state.State

data class ProfileState(
        val isLoading : Boolean = false ,
        val error : String? = null ,
        val isConnectivityAvailable: Boolean? = null ,
        val data : User ,
        val isUserLoggedIn : Boolean? = null
) : State
