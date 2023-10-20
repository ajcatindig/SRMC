package com.example.srmc.data.remote.api

import com.example.srmc.data.remote.model.response.DoctorResponse
import com.example.srmc.data.remote.model.response.DoctorSchedules
import com.example.srmc.data.remote.model.response.DoctorSlots
import com.example.srmc.data.remote.model.response.DoctorsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DoctorService
{
    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @GET("api/users/doctors")
    suspend fun getAllDoctors() : Response<DoctorsResponse>

    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @GET("api/users/doctor/{id}")
    suspend fun getDoctorById(@Path("id") id : Int) : Response<DoctorResponse>

    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @GET("api/users/doctor/{id}/dates")
    suspend fun getDoctorSchedules(@Path("id") id : Int) : Response<DoctorSchedules>

    @Headers("Accept: application/json", "X-Requested-With: XMLHttpRequest")
    @GET("api/users/doctor/{id}/{date}/times")
    suspend fun getDoctorSlots(@Path("id") id: Int, @Path("date") date: String): Response<DoctorSlots>

}