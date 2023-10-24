package com.example.srmc.repository

import android.util.Log
import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Slots
import com.example.srmc.core.repository.DoctorRepository
import com.example.srmc.core.repository.Either
import com.example.srmc.data.remote.api.DoctorService
import com.example.srmc.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Source of data of doctors from network
 */
@Singleton
class DoctorRepositoryImpl @Inject internal constructor(
        private val doctorService : DoctorService
) : DoctorRepository
{
    override fun getAllDoctors() : Flow<Either<List<Doctor>>> = flow {
        val doctorResponse = doctorService.getAllDoctors().getResponse()

        val status = when(doctorResponse.status) {
            200 -> Either.success(doctorResponse.data)
            else -> Either.error(doctorResponse.message!!)
        }
        emit(status)
        Log.d("Data", "${doctorResponse.data}")
    }.catch {
        Log.e("DoctorRepositoryImpl", "catch ${it.message!!}")
        emit(Either.error("An unknown error occurred"))
    }

    override fun getDoctorById(id : Int) : Flow<Doctor> = flow<Doctor> {
        val doctorResponse = doctorService.getDoctorById(id).getResponse()

        when(doctorResponse.status) {
            200 -> Either.success(emit(doctorResponse.data))
            else -> Either.error(doctorResponse.message!!)
        }
        Log.d("Data", "${doctorResponse.data}")
    }.catch {
        Log.e("DoctorRepositoryImpl", "catch ${it.message!!}")
    }


    override fun getDoctorSchedules(id : Int) : Flow<Either<List<Schedules>>> = flow {
        val doctorScheduleResponse = doctorService.getDoctorSchedules(id).getResponse()

        val status = when(doctorScheduleResponse.status) {
            200 -> Either.success(doctorScheduleResponse.data)
            else -> Either.error(doctorScheduleResponse.message!!)
        }
        emit(status)
        Log.d("Data", "${doctorScheduleResponse.data}")
    }.catch {
        Log.e("DoctorRepositoryImpl", "catch ${it.message!!}")
        emit(Either.error("An unknown error occurred"))
    }

    override fun getDoctorScheduleByDate(id : Int , date : String) : Flow<Schedules> = flow<Schedules> {
        val doctorSched = doctorService.getDoctorSchedule(id, date).getResponse()

        when(doctorSched.status) {
            200 -> Either.success(emit(doctorSched.data))
            404 -> Either.notFound(doctorSched.message!!)
            else -> Either.error(doctorSched.message!!)
        }
        Log.d("Data", "${doctorSched.data}")
    }.catch {
    Log.e("DoctorRepositoryImpl", "catch ${it.message!!}")
    }


    override fun getDoctorSlots(id : Int , date : String) : Flow<Either<List<Slots>>> = flow {
        val doctorSlotsResponse = doctorService.getDoctorSlots(id, date).getResponse()

        val status = when(doctorSlotsResponse.status) {
            200 -> Either.success(doctorSlotsResponse.data)
            else -> Either.error(doctorSlotsResponse.message!!)
        }
        emit(status)
        Log.d("Data", "${doctorSlotsResponse.data}")
    }.catch {
        Log.e("DoctorRepositoryImpl", "catch ${it.message!!}")
        emit(Either.error("An unknown error occurred"))
    }

}