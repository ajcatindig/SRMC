package com.example.srmc.data.remote.api

import com.example.srmc.data.remote.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService
{
    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @GET("api/user")
    suspend fun getUserProfile() : Response<UserResponse>
}