package com.example.srmc.data.remote.model.response

data class ForgotResponse(
        override val status : Status,
        override val message : String?
) : BaseResponse
