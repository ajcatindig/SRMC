package com.example.srmc.data.remote.api

import com.example.srmc.data.remote.model.request.ForgotRequest
import com.example.srmc.data.remote.model.request.LoginRequest
import com.example.srmc.data.remote.model.request.RegisterRequest
import com.example.srmc.data.remote.model.response.AuthResponse
import com.example.srmc.data.remote.model.response.ForgotResponse
import com.example.srmc.data.remote.model.response.LoginResponse
import com.example.srmc.data.remote.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService
{
    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @POST("/login/mobile")
    suspend fun login(@Body authRequest : LoginRequest) : Response<AuthResponse>

    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @POST("/register")
    suspend fun register(@Body authRequest : RegisterRequest) : Response<RegisterResponse>

    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @POST("/password-reset")
    suspend fun forgotPass(@Body authRequest : ForgotRequest) : Response<ForgotResponse>

}