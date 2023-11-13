package com.example.srmc.view.viewmodel.form

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.form.AppointmentFormState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentFormViewModel @Inject constructor(
        @RemoteRepository val appointmentRepository : AppointmentRepository
) : BaseViewModel<AppointmentFormState>(initialState = AppointmentFormState())
{
    fun setDoctorId(id : Int) {
        setState { state -> state.copy(doctor_id = id) }
    }

    fun setType(type : Int) {
        setState { state -> state.copy(type = type) }
    }

    fun setDate(date : String) {
        setState { state -> state.copy(date = date) }
    }

    fun setTime(time : String) {
        setState { state -> state.copy(time = time) }
    }

    fun postAppointment(id : Int, date : String) {
        viewModelScope.launch {
            val type = currentState.type
            val doctor_id = currentState.doctor_id
            val sched = currentState.date
            val time = currentState.time

            setState { state -> state.copy(isLoading = true) }

            val response = appointmentRepository.postAppointment(type, id, date, time)

            response.onSuccess { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = message.message,
                               error = null)
                }
                Log.d("AppointFormViewModel", "$message")
            }.onUnprocessable { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = null,
                               error = message)
                }
                Log.e("AppointFormViewModel", "$message")
            }.onError { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = null,
                               error = "Something went wrong")
                }
                Log.e("AppointFormViewModel", "$message")
            }
        }
    }
}