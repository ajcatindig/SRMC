package com.example.srmc.view.viewmodel.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.model.Schedules
import com.example.srmc.core.repository.DoctorRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.form.ListSchedState
import com.example.srmc.view.viewmodel.BaseViewModel
import com.example.srmc.view.viewmodel.detail.DoctorDetailViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchedulesViewModel @AssistedInject constructor(
        @Assisted private val id : Int,
        @RemoteRepository val doctorRepository : DoctorRepository,
        private val connectivityObserver : ConnectivityObserver
) : BaseViewModel<ListSchedState>(initialState = ListSchedState())
{
    init
    {
        loadSchedules()
        observeConnectivity()
    }

    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    fun loadSchedules() {
        doctorRepository.getDoctorSchedules(id)
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

    @AssistedFactory
    interface Factory {
        fun create(id : Int) : SchedulesViewModel
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