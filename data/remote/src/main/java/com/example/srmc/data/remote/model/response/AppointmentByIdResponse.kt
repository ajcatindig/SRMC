package com.example.srmc.data.remote.model.response

import com.example.srmc.core.model.Appointments

data class AppointmentByIdResponse(
        val status : Int,
        val data : Appointments,
        val message : String?
)
