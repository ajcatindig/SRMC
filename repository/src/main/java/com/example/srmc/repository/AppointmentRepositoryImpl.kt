package com.example.srmc.repository

import android.util.Log
import com.example.srmc.core.model.AppointmentResult
import com.example.srmc.core.model.Appointments
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.core.repository.Either
import com.example.srmc.data.remote.api.AppointmentService
import com.example.srmc.data.remote.model.request.AppointmentRequest
import com.example.srmc.data.remote.model.request.ReschedRequest
import com.example.srmc.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepositoryImpl @Inject internal constructor(
        private val appointmentService : AppointmentService
) : AppointmentRepository
{
    override suspend fun postAppointment(
            type : Int,
            doctor_id : Int ,
            date : String ,
            time : String) : Either<AppointmentResult>
    {
        return runCatching {
            val appointmentResponse = appointmentService.postAppointment(
                    AppointmentRequest(
                            type = type,
                            doctor_id = doctor_id,
                            date = date,
                            time = time))
                    .getResponse()

            when(appointmentResponse.status) {
                200 -> Either.success(AppointmentResult("Your request has been submitted. Please wait for an email regarding the confirmation of your request"))
                422 -> Either.unprocessable(appointmentResponse.message!!)
                else -> Either.error(appointmentResponse.message!!)
            }
        }.getOrDefault(Either.error("Something went wrong!"))
    }

    override suspend fun reschedAppointment(
            id : Int ,
            date : String ,
            time : String) : Either<AppointmentResult>
    {
        return runCatching {
            val reschedResponse = appointmentService.rescheduleAppointment(
                    ReschedRequest(id = id, date = date, time = time)).getResponse()

            when(reschedResponse.status) {
                200 -> Either.success(AppointmentResult("Your request has been submitted. Please wait for an email regarding the confirmation of your request"))
                422 -> Either.unprocessable(reschedResponse.message!!)
                else -> Either.error(reschedResponse.message!!)
            }
        }.getOrDefault(Either.error("Something went wrong!"))
    }

    override suspend fun cancelAppointment(id : Int) : Either<AppointmentResult>
    {
        return runCatching {
            val cancelResponse = appointmentService.cancelAppointment(id).getResponse()

            when(cancelResponse.status) {
                200 -> Either.success(AppointmentResult("Appointment Cancelled"))
                422 -> Either.unprocessable(cancelResponse.message!!)
                else -> Either.error(cancelResponse.message!!)
            }
        }.getOrDefault(Either.error("Something went wrong!"))
    }

    override fun getAppointments() : Flow<Either<List<Appointments>>> = flow {
        val appointmentResponse = appointmentService.getAppointments().getResponse()

        val status = when(appointmentResponse.status) {
            200 -> Either.success(appointmentResponse.data)
            else -> Either.error(appointmentResponse.message!!)
        }
        emit(status)
        Log.d("Data", "${appointmentResponse.data}")
    }.catch {
        Log.e("ApptRepositoryImpl", "catch ${it.message!!}")
        emit(Either.error("An unknown error occurred"))
    }

    override fun getAppointmentById(id : Int) : Flow<Appointments>  = flow<Appointments> {
        val appointmentResponse = appointmentService.getAppointmentById(id).getResponse()

        when(appointmentResponse.status) {
            200 -> Either.success(emit(appointmentResponse.data))
            else -> Either.error(appointmentResponse.message!!)
        }
        Log.d("Data", "${appointmentResponse.data}")
    }.catch {
        Log.e("ApptRepositoryImpl", "catch ${it.message!!}")
    }

}