package com.example.srmc.repository

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
        return runCatching {
            val registerResponse = authService.register(
                    RegisterRequest(username, email, password, password_confirmation)).getResponse()

            val apiStatus = registerResponse.status

            val eitherResult = when (apiStatus) {
                Status.SUCCESS -> Either.success(RegisterResult(registerResponse.message.toString()))
                Status.NOT_FOUND -> Either.notFound(registerResponse.message.toString())
                Status.FORBIDDEN -> Either.forbidden(registerResponse.message.toString())
                Status.UNPROCESSABLE -> Either.unprocessable(registerResponse.message.toString())
                Status.SERVER_ERROR -> Either.serverError(registerResponse.message.toString())
                else -> Either.error(registerResponse.message.toString())
            }

            eitherResult
        }.getOrDefault(Either.error("Something went wrong!"))
    }


    override suspend fun getUserByEmailAndPassword(
            email : String ,
            password : String
    ) : Either<AuthCredential>
    {
        return runCatching {
            val authResponse = authService.login(LoginRequest(email, password)).getResponse()

            val apiStatus = authResponse.status

            val eitherResult = when(apiStatus) {
                Status.SUCCESS -> Either.success(AuthCredential(authResponse.token!!))
                Status.NOT_FOUND -> Either.notFound(authResponse.message.toString())
                Status.FORBIDDEN -> Either.forbidden(authResponse.message.toString())
                Status.UNPROCESSABLE -> Either.unprocessable(authResponse.message.toString())
                Status.SERVER_ERROR -> Either.serverError(authResponse.message.toString())
                else -> Either.error(authResponse.message.toString())
            }
            eitherResult
        }.getOrDefault(Either.error("Something went wrong!"))
    }

    override suspend fun forgotPassword(email : String) : Either<ForgotResult>
    {
        return runCatching {
            val forgotResponse = authService.forgotPass(ForgotRequest(email)).getResponse()

            val apiStatus = forgotResponse.status

            val eitherResult = when(apiStatus) {
                Status.SUCCESS -> Either.success(ForgotResult(forgotResponse.message.toString()))
                Status.NOT_FOUND -> Either.notFound(forgotResponse.message.toString())
                Status.FORBIDDEN -> Either.forbidden(forgotResponse.message.toString())
                Status.UNPROCESSABLE -> Either.unprocessable(forgotResponse.message.toString())
                Status.SERVER_ERROR -> Either.serverError(forgotResponse.message.toString())
                else -> Either.error(forgotResponse.message.toString())
            }
            eitherResult
        }.getOrDefault(Either.error("Something went wrong!"))
    }

}