package com.example.srmc.data.remote.model.response

import com.example.srmc.core.model.Appointments

data class AppointmentListResponse(
        val status : Int,
        val data : List<Appointments> = emptyList(),
        val message : String?
)
