package com.example.srmc.view.viewmodel.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.model.Doctor
import com.example.srmc.core.model.Schedules
import com.example.srmc.core.repository.DoctorRepository
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.detail.SchedDetailState
import com.example.srmc.view.viewmodel.BaseViewModel
import com.example.srmc.view.viewmodel.form.SlotsViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ScheduleDetailViewModel @AssistedInject constructor(
        @Assisted private val id : Int ,
        @Assisted private val date : String,
        @RemoteRepository val doctorRepository : DoctorRepository ,
        private val connectivityObserver : ConnectivityObserver
) : BaseViewModel<SchedDetailState>(initialState = SchedDetailState(
        data = Schedules(
                id = null,
                user_id = null,
                day_id = null,
                start_time = null,
                end_time = null,
                date_label = null,
                date = null,
                slots = null)))
{
    private lateinit var selectedSchedule : Schedules

    init
    {
        observeConnectivity()
        loadSchedule()
    }

    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    fun loadSchedule() {
        viewModelScope.launch {
            setState { state -> state.copy(isLoading = true) }
            val schedule = doctorRepository.getDoctorScheduleByDate(id, date).firstOrNull()
            if (schedule != null) {
                selectedSchedule = schedule
                setState { state ->
                    state.copy(isLoading = false, data = schedule)
                }
                Log.d("Schedule Data", "${schedule}")
            } else {
                setState { state -> state.copy(isLoading = false, error = "An unknown error occurred!") }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id : Int, date : String) : ScheduleDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
                assistedFactory : Factory ,
                id : Int,
                date : String) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass : Class<T>) : T {
                return assistedFactory.create(id, date) as T
            }
        }
    }
}