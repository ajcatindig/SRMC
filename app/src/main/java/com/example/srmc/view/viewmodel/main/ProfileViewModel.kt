package com.example.srmc.view.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.preference.PreferenceManager
import com.example.srmc.view.state.main.ProfileState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val preferenceManager : PreferenceManager,
        private val connectivityObserver : ConnectivityObserver
) : BaseViewModel<ProfileState>(initialState = ProfileState())
{
    init {
        observeConnectivity()
    }
    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}