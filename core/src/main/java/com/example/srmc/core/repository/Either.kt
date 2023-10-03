package com.example.srmc.core.repository

sealed class Either<T> {
    data class Success<T>(val data : T) : Either<T>()
    data class NotFound<T>(val message : String) : Either<T>()
    data class Forbidden<T>(val message : String) : Either<T>()
    data class Unprocessable<T>(val message : String) : Either<T>()
    data class ServerError<T>(val message : String) : Either<T>()

    companion object {
        fun <T> success(data : T) = Success(data)
        fun <T> notFound(message : String) = NotFound<T>(message)
        fun <T> forbidden(message : String) = Forbidden<T>(message)
        fun <T> unprocessable(message : String) = Unprocessable<T>(message)
        fun <T> serverError(message : String) = ServerError<T>(message)
    }

    inline fun onSuccess(block : (T) -> Unit) : Either<T> = apply {
        if (this is Success) {
            block(data)
        }
    }
    inline fun onNotFound(block : (String) -> Unit) : Either<T> = apply {
        if (this is NotFound) {
            block(message)
        }
    }
    inline fun onForbidden(block : (String) -> Unit) : Either<T> = apply {
        if (this is Forbidden) {
            block(message)
        }
    }
    inline fun onUnprocessable(block : (String) -> Unit) : Either<T> = apply {
        if (this is Unprocessable) {
            block(message)
        }
    }
    inline fun onServerError(block : (String) -> Unit) : Either<T> = apply {
        if (this is ServerError) {
            block(message)
        }
    }
}
