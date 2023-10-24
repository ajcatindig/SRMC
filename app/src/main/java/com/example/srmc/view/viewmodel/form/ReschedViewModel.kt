package com.example.srmc.view.viewmodel.form

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.form.ReschedFormState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReschedViewModel @Inject constructor(
    @RemoteRepository val appointmentRepository : AppointmentRepository
) : BaseViewModel<ReschedFormState>(initialState = ReschedFormState())
{
    fun setId(id : Int) {
        setState { state -> state.copy(id = id) }
    }

    fun setSchedDate(date : String) {
        setState { state -> state.copy(date = date) }
    }

    fun setSchedTime(time : String) {
        setState { state -> state.copy(time = time) }
    }

    fun reschedAppointment(id : Int, date : String)
    {
        viewModelScope.launch {
            val appointment_id = currentState.id
            val sched = currentState.date
            val time = currentState.time

            setState { state -> state.copy(isLoading = true) }

            val response = appointmentRepository.reschedAppointment(id, date, time)

            response.onSuccess { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = message.message,
                               error = null)
                }
                Log.d("ReschedViewModel" , "$message")
            }.onUnprocessable { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = null,
                               error = message)
                }
                Log.e("ReschedViewModel" , "$message")
            }.onError { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = null,
                               error = "Something went wrong")
                }
                Log.e("ReschedViewModel", "$message")
            }
        }
    }


}