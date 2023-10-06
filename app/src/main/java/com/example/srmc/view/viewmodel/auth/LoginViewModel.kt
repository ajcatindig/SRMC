package com.example.srmc.view.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.example.srmc.core.repository.AuthRepository
import com.example.srmc.view.state.auth.LoginState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.srmc.core.session.SessionManager
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val loginRepository : AuthRepository ,
        private val sessionManager : SessionManager
) : BaseViewModel<LoginState>(initialState = LoginState())
{
    init {
        val isLoggedIn = sessionManager.getToken() != null
        setState { LoginState(isLoggedIn = isLoggedIn) }
    }

    fun setEmail(email : String)
    {
        setState { state -> state.copy(email = email) }
    }

    fun setPassword(password : String)
    {
        setState { state -> state.copy(password = password) }
    }

    fun login()
    {
        viewModelScope.launch {
            val email = currentState.email
            val password = currentState.password

            setState { state -> state.copy(isLoading = true) }

            val response = loginRepository.getUserByEmailAndPassword(email, password)

            response.onSuccess { authCredential ->
                sessionManager.saveToken(authCredential.token)
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            error = null)
                }
            }.onNotFound { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message)
                }
            }.onForbidden { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message)
                }
            }.onUnprocessable { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message)
                }
            }.onServerError { message ->
                setState { state->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message)
                }
            }.onError { message ->
                setState { state->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = "Something went wrong, please try again")
                }
            }
        }
    }

}