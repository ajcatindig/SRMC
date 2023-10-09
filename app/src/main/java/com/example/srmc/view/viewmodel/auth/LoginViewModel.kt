package com.example.srmc.view.viewmodel.auth

import android.util.Log
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

    fun login() {
        viewModelScope.launch {
            val email = currentState.email
            val password = currentState.password

            setState { state -> state.copy(isLoading = true) }

            Log.d("LoginViewModel", "Logging in with email: $email")

            val response = loginRepository.getUserByEmailAndPassword(email, password)

            response.onSuccess { authCredential ->
                sessionManager.saveToken(authCredential.token)
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            error = null,
                            email = "",
                            password = ""
                              )
                }
            }.onNotFound { message ->
                Log.d("LoginViewModel", "Login failed with not found: $message")
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message,
                            email = "",
                            password = ""
                              )
                }
            }.onForbidden { message ->
                Log.d("LoginViewModel", "Login failed with forbidden: $message")
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message,
                            email = "",
                            password = ""
                              )
                }
            }.onUnprocessable { message ->
                Log.d("LoginViewModel", "Login failed with unprocessable: $message")
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message,
                            email = "",
                            password = ""
                              )
                }
            }.onServerError { message ->
                Log.d("LoginViewModel", "Login failed with server error: $message")
                setState { state->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = message,
                            email = "",
                            password = ""
                              )
                }
            }.onError { message ->
                Log.e("LoginViewModel", "Login failed with error: $message")
                setState { state->
                    state.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            error = "Something went wrong, please try again.",
                            email = "",
                            password = ""
                              )
                }
            }
        }
    }



}