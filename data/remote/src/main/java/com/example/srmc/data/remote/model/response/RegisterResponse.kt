package com.example.srmc.data.remote.model.response

data class RegisterResponse(
        override val status : Status,
        override val message : String?
) : BaseResponse
