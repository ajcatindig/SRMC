package com.example.srmc.data.remote.model.request

data class RegisterRequest(
        val email : String,
        val password : String,
        val password_confirmation : String)
