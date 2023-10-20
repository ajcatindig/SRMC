package com.example.srmc.core.repository

import com.example.srmc.core.model.AppointmentResult
import javax.inject.Singleton

@Singleton
interface AppointmentRepository
{
    suspend fun postAppointment(
            doctor_id : Int,
            date : String,
            time : String
    ) : Either<AppointmentResult>


}