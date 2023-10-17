package com.example.srmc.data.remote.model.response

import com.example.srmc.core.model.User

data class UserResponse(
        val data : User ,
        val status : Int,
        val message : String?
)
