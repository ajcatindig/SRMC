package com.example.srmc.view.viewmodel.form

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.form.CancelState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CancelViewModel @Inject constructor(
        @RemoteRepository val appointmentRepository : AppointmentRepository
) : BaseViewModel<CancelState>(initialState = CancelState())
{
    fun setId(id : Int) {
        setState { state -> state.copy(id = id) }
    }

    fun cancelAppointment(id : Int)
    {
        viewModelScope.launch {
            val appointment_id = currentState.id

            val response = appointmentRepository.cancelAppointment(id)

            response.onSuccess { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = message.message,
                               error = null)
                }
                Log.d("CancelViewModel", "$message")
            }.onUnprocessable { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = null,
                               error = message)
                }
                Log.e("CancelViewModel", "$message")
            }.onError { message ->
                setState { state ->
                    state.copy(isLoading = false,
                               isSuccess = null,
                               error = "Something went wrong")
                }
                Log.e("CancelViewModel", "$message")
            }
        }
    }
}