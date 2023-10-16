package com.example.srmc.repository

import android.util.Log
import com.example.srmc.core.model.Dates
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


    override fun getDoctorSchedules(id : Int) : Flow<Either<List<Dates>>> = flow {
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


    override fun getDoctorSlots(id : Int , date : String) : Flow<Either<List<Slots>>> = flow<Either<List<Slots>>> {
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