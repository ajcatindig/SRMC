package com.example.srmc.view.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.preference.PreferenceManager
import com.example.srmc.core.repository.AppointmentRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.main.AppointmentState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
        private val preferenceManager : PreferenceManager,
        private val connectivityObserver : ConnectivityObserver,
        @RemoteRepository val appointmentRepository : AppointmentRepository
) : BaseViewModel<AppointmentState>(initialState = AppointmentState())
{
    init {
        getAllAppointments()
        observeConnectivity()
    }
    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    fun getAllAppointments() {
        appointmentRepository.getAppointments()
                .distinctUntilChanged()
                .onEach { response ->
                    response.onSuccess { data ->
                        setState { state -> state.copy(isLoading = false, data = data) }
                    }.onError { message ->
                        setState { state -> state.copy(isLoading = false, error = message) }
                    }
                }.onStart { setState { state -> state.copy(isLoading = true) } }
                .launchIn(viewModelScope)
    }


    companion object {
        private const val TAG = "AppointmentViewModel"
    }
}