package com.example.srmc.core.repository

import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Slots
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 * Repository for Doctor
 */
@Singleton
interface DoctorRepository
{
    /**
     * Returns all doctors
     */
    fun getAllDoctors() : Flow<Either<List<Doctor>>>

    /**
     * Returns doctor by ID
     */
    fun getDoctorById(id : Int) : Flow<Doctor>

    /**
     * Returns all doctor schedules
     */
    fun getDoctorSchedules(id : Int) : Flow<Either<List<Schedules>>>

    /**
     * Return specified doctor schedule
     */
    fun getDoctorScheduleByDate(id : Int, date : String) : Flow<Schedules>

    /**
     * Returns all schedule slots
     */
    fun getDoctorSlots(id : Int, date : String) : Flow<Either<List<Slots>>>
}