package com.example.srmc.repository

import android.util.Log
import com.example.srmc.core.model.AuthCredential
import com.example.srmc.core.model.ForgotResult
import com.example.srmc.core.model.LoginResult
import com.example.srmc.core.model.RegisterResult
import com.example.srmc.core.repository.AuthRepository
import com.example.srmc.core.repository.Either
import com.example.srmc.data.remote.api.AuthService
import com.example.srmc.data.remote.model.request.ForgotRequest
import com.example.srmc.data.remote.model.request.LoginRequest
import com.example.srmc.data.remote.model.request.RegisterRequest
import com.example.srmc.data.remote.model.response.LoginResponse
import com.example.srmc.data.remote.model.response.Status
import com.example.srmc.data.remote.util.getResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject internal constructor(
        private val authService : AuthService
) : AuthRepository
{
    override suspend fun addUser(
            username: String,
            email: String,
            password: String,
            password_confirmation: String
    ): Either<RegisterResult>
    {
        TODO()
    }


    override suspend fun getUserByEmailAndPassword(
            email: String,
            password: String): Either<AuthCredential>
    {
        return runCatching {
            val authResponse = authService.login(LoginRequest(email, password)).getResponse()

            when(authResponse.status) {
                200 -> Either.success(AuthCredential(authResponse.token!!))
                403 -> Either.forbidden(authResponse.message.toString())
                404 -> Either.notFound(authResponse.message.toString())
                422 -> Either.unprocessable(authResponse.message.toString())
                500 -> Either.serverError(authResponse.message.toString())
                else -> Either.error(authResponse.message.toString())
            }
        }.getOrDefault(Either.error("Something went wrong!"))
    }





    override suspend fun forgotPassword(email : String) : Either<ForgotResult>
    {
        TODO()
    }

}