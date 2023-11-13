package com.example.srmc.data.remote.model.request

data class AppointmentRequest(
        val type : Int,
        val doctor_id : Int,
        val date : String,
        val time : String
)
