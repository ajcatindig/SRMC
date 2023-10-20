package com.example.srmc.data.remote.api

import com.example.srmc.data.remote.model.request.AppointmentRequest
import com.example.srmc.data.remote.model.response.AppointmentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AppointmentService
{
    @Headers("Accept: application/json" , "X-Requested-With: XMLHttpRequest")
    @POST("api/appointment")
    suspend fun postAppointment(@Body appointmentRequest : AppointmentRequest) : Response<AppointmentResponse>

}