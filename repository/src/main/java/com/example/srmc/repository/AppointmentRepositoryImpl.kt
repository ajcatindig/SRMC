package com.example.srmc.repository

import com.example.srmc.core.model.AppointmentResult
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.core.repository.Either
import com.example.srmc.data.remote.api.AppointmentService
import com.example.srmc.data.remote.model.request.AppointmentRequest
import com.example.srmc.data.remote.util.getResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepositoryImpl @Inject internal constructor(
        private val appointmentService : AppointmentService
) : AppointmentRepository
{
    override suspend fun postAppointment(
            doctor_id : Int ,
            date : String ,
            time : String) : Either<AppointmentResult>
    {
        return runCatching {
            val appointmentResponse = appointmentService.postAppointment(
                    AppointmentRequest(
                            doctor_id = doctor_id,
                            date = date,
                            time = time))
                    .getResponse()

            when(appointmentResponse.status) {
                200 -> Either.success(AppointmentResult(appointmentResponse.message.toString()))
                422 -> Either.unprocessable(appointmentResponse.message!!)
                else -> Either.error(appointmentResponse.message!!)
            }
        }.getOrDefault(Either.error("Something went wrong!"))
    }
}