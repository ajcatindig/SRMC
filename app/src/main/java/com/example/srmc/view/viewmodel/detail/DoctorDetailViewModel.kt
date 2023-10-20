package com.example.srmc.view.viewmodel.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.repository.DoctorRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.detail.DoctorDetailState
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

class DoctorDetailViewModel @AssistedInject constructor(
        @Assisted private val id : Int ,
        @RemoteRepository val doctorRepository : DoctorRepository,
        private val connectivityObserver : ConnectivityObserver
) : BaseViewModel<DoctorDetailState>(initialState = DoctorDetailState(
        data = Doctor(
                id = null,
                name = null,
                title = null,
                email = null,
                contact_number = null,
                slots = null,
                type = null,
                profile_photo_path = null)))
{
    private lateinit var selectedDoctor : Doctor

    init
    {
        loadDoctor()
        observeConnectivity()
    }

    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    fun loadDoctor() {
        viewModelScope.launch {
            setState { state -> state.copy(isLoading = true) }
            val doctor = doctorRepository.getDoctorById(id).firstOrNull()
            if (doctor != null) {
                selectedDoctor = doctor
                setState { state ->
                    state.copy(isLoading = false, data = doctor)
                }
                Log.d("Doctor Data" ,"${doctor}")
            } else {
                setState { state -> state.copy(isLoading = false, error = "An unknown error occurred") }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id : Int) : DoctorDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
                assistedFactory: Factory,
                id: Int): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(id) as T
            }
        }
    }
}