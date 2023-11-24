package com.example.srmc.view.viewmodel.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.model.Appointments
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.detail.AppointmentDetailState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppointmentDetailViewModel @AssistedInject constructor(
        @Assisted private val id : Int,
        @RemoteRepository val appointmentRepository : AppointmentRepository,
        private val connectivityObserver : ConnectivityObserver
) : BaseViewModel<AppointmentDetailState>(initialState = AppointmentDetailState(
        data = Appointments(
                id = null, type = null, patient_id = null, doctor_id = null,
                start_time = null, end_time = null,
                follow_up_start_time = null, follow_up_end_time = null,
                check_in = null, check_out = null,
                accepted_at = null, cancelled_at = null, scheduled_at = null, rescheduled_at = null, followed_up_at = null, created_at = null,
                verified_at = null, payment_link = null,
                doctor_name = null,
                meeting_link = null,
                doctor = Doctor(id = null, name = null, email = null, contact_number = null, slots = null, type = null, profile_photo_path = null, title = null))))
{
    private lateinit var selectedAppointment : Appointments

    init
    {
        loadAppointment()
        observeConnectivity()
    }

    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    fun loadAppointment() {
        viewModelScope.launch {
            setState { state -> state.copy(isLoading = true) }
            val appointment = appointmentRepository.getAppointmentById(id).firstOrNull()
            if (appointment != null ) {
                selectedAppointment = appointment
                setState { state ->
                    state.copy(isLoading = false, data = appointment)
                }
                Log.d("Appointment Data", "$appointment")
            } else {
                setState { state -> state.copy(isLoading = false, error = "An unknown error occurred") }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id : Int) : AppointmentDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
                assistedFactory : Factory,
                id : Int) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}