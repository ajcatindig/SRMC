package com.example.srmc.data.remote.api

import com.example.srmc.data.remote.model.request.AppointmentRequest
import com.example.srmc.data.remote.model.request.ReschedRequest
import com.example.srmc.data.remote.model.response.AppointmentByIdResponse
import com.example.srmc.data.remote.model.response.AppointmentListResponse
import com.example.srmc.data.remote.model.response.AppointmentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AppointmentService
{
    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @POST("api/appointment")
    suspend fun postAppointment(@Body appointmentRequest : AppointmentRequest) : Response<AppointmentResponse>

    @Headers("Accept: application/json", "X-Requested-With: XMLHttpRequest")
    @GET("api/appointments/all")
    suspend fun getAppointments() : Response<AppointmentListResponse>

    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @GET("api/appointment/{id}")
    suspend fun getAppointmentById(@Path("id") id : Int) : Response<AppointmentByIdResponse>

    @Headers("Accept: application/json", "X-Requested-With: XMLHttpRequest")
    @PUT("api/appointment/reschedule")
    suspend fun rescheduleAppointment(@Body reschedRequest : ReschedRequest) : Response<AppointmentResponse>

    @Headers("Accept: application/json", "X-Requested-With: XMLHttpRequest")
    @PATCH("api/appointment/cancel/{id}")
    suspend fun cancelAppointment(@Path("id") id : Int) : Response<AppointmentResponse>

}