package com.example.srmc.data.remote.model.response


interface BaseResponse {
    val status: Status
    val message: String?
}

enum class Status(val value: Int) {
    SUCCESS(200),
    NOT_FOUND(404),
    FORBIDDEN(403),
    UNPROCESSABLE(422),
    SERVER_ERROR(500)
}
