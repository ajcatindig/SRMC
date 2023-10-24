package com.example.srmc.core.repository

import com.example.srmc.core.model.AppointmentResult
import com.example.srmc.core.model.Appointments
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface AppointmentRepository
{
    suspend fun postAppointment(
            type : String,
            doctor_id : Int,
            date : String,
            time : String
    ) : Either<AppointmentResult>

    suspend fun reschedAppointment(
            id : Int,
            date : String,
            time : String
    ) : Either<AppointmentResult>

    suspend fun cancelAppointment(id : Int) : Either<AppointmentResult>

    fun getAppointments() : Flow<Either<List<Appointments>>>

    fun getAppointmentById(id : Int) : Flow<Appointments>


}