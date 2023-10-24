package com.example.srmc.core.model

data class Appointments(
        val id : Int?,
        val type : String?,
        val patient_id : String?,
        val doctor_id : String?,
        val start_time : String?,
        val end_time : String?,
        val follow_up_start_time : String?,
        val follow_up_end_time : String?,
        val check_in : String?,
        val check_out : String?,
        val accepted_at : String?,
        val cancelled_at : String?,
        val scheduled_at : String?,
        val rescheduled_at : String?,
        val followed_up_at : String?,
        val created_at : String?,
        val verified_at : String?,
        val payment_link : String?,
        val doctor_name : String?,
        val doctor : Doctor
)
