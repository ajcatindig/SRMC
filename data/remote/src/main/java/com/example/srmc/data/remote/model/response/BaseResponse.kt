package com.example.srmc.data.remote.model.response

import android.util.Log


interface BaseResponse {
    val status: Status
    val message: String?
}

sealed class Status {
    object SUCCESS : Status() {
        const val code = 200
    }
    object NOT_FOUND : Status() {
        const val code = 404
    }
    object FORBIDDEN : Status() {
        const val code = 403
    }
    object UNPROCESSABLE : Status() {
        const val code = 422
    }
    object SERVER_ERROR : Status() {
        const val code = 500
    }
}






