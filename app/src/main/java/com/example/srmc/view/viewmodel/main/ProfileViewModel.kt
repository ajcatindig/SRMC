package com.example.srmc.view.viewmodel.main

import androidx.lifecycle.viewModelScope
import com.example.srmc.core.connectivity.ConnectionState
import com.example.srmc.core.connectivity.ConnectivityObserver
import com.example.srmc.core.model.User
import com.example.srmc.core.preference.PreferenceManager
import com.example.srmc.core.repository.UserRepository
import com.example.srmc.core.session.SessionManager
import com.example.srmc.di.RemoteRepository
import com.example.srmc.view.state.main.ProfileState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val preferenceManager : PreferenceManager,
        private val connectivityObserver : ConnectivityObserver,
        private val sessionManager : SessionManager,
        @RemoteRepository val userRepository : UserRepository
) : BaseViewModel<ProfileState>(initialState = ProfileState(data = User(
        id = null,
        name = null,
        title = null,
        email = null,
        birthdate = null,
        contact_number = null,
        profile_photo_path = null,
        address = null,
        sex = null)))
{
    init {
        observeConnectivity()
        checkUserSession()
        getCurrentUser()
    }
    private fun observeConnectivity() {
        connectivityObserver.connectionState
                .distinctUntilChanged()
                .map { it === ConnectionState.Available }
                .onEach { setState { state -> state.copy(isConnectivityAvailable = it) } }
                .launchIn(viewModelScope)
    }

    fun getCurrentUser()
    {
        userRepository.getCurrentUser()
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

    fun logout()
    {
        viewModelScope.launch {
            sessionManager.saveToken(null)
            sessionManager.saveEmail(null)
            setState { state -> state.copy(isUserLoggedIn = false) }
        }
    }

    private fun checkUserSession() {
        setState { it.copy(isUserLoggedIn = sessionManager.getToken() != null) }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}