package com.example.srmc.data.remote.model.response

data class AuthResponse(
        override val status : Status ,
        override val message : String? ,
        val token : String?
) : BaseResponse
