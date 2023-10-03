package com.example.srmc.data.remote.model.response

import javax.net.ssl.SSLEngineResult.Status

interface BaseResponse {
    val status: Status
    val message: String
}

enum class Status {
    SUCCESS {
      override val value : Int = 200
    },
    NOT_FOUND {
        override val value : Int = 404
    },
    FORBIDDEN {
        override val value : Int = 403
    },
    UNPROCESSABLE {
        override val value: Int = 422
    },
    SERVER_ERROR {
        override val value : Int = 500
    };
    abstract val value : Int
}