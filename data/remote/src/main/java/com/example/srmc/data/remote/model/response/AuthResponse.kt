package com.example.srmc.data.remote.model.response

data class AuthResponse(
        val status : Int ,
        val message : String? ,
        val token : String?
)
