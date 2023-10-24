package com.example.srmc.data.remote.model.response

import com.example.srmc.core.model.Schedules
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Slots

data class DoctorsResponse(
        val status : Int,
        val data : List<Doctor> = emptyList(),
        val message : String?
)

data class DoctorResponse(
        val status : Int,
        val data : Doctor,
        val message : String?
)

data class DoctorSchedules(
        val status : Int ,
        val data : List<Schedules> = emptyList() ,
        val message : String?
)

data class DoctorSchedule(
        val status : Int,
        val data : Schedules,
        val message : String?
)

data class DoctorSlots(
        val status : Int,
        val data : List<Slots> = emptyList(),
        val message : String?
)


