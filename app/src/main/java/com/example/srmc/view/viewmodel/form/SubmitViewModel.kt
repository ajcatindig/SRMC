package com.example.srmc.view.viewmodel.form

import com.example.srmc.view.state.form.SubmitFormState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(

) : BaseViewModel<SubmitFormState>(initialState = SubmitFormState())
{
    fun setDoctorId(id : Int) {
        setState { state -> state.copy(doctor_id = id) }
    }

    fun setSchedDate(date : String) {
        setState { state -> state.copy(scheduleDate = date) }
    }

    fun setSchedSlot(slot : String) {
        setState { state -> state.copy(scheduleSlot = slot) }
    }
}