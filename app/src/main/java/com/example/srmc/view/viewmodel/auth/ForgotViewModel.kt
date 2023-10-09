package com.example.srmc.view.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.example.srmc.core.repository.AuthRepository
import com.example.srmc.utils.validator.AuthValidator
import com.example.srmc.view.state.auth.ForgotState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
        private val forgotRepository : AuthRepository
) : BaseViewModel<ForgotState>(initialState = ForgotState())
{
    fun setEmail(email : String) {
        setState { state -> state.copy(email = email) }
    }

    fun sendEmail()
    {
        if (! validateCredentials()) return

        viewModelScope.launch {
            val email = currentState.email

            setState { state -> state.copy(isLoading = true) }

            val response = forgotRepository.forgotPassword(email)

            response.onSuccess { message ->
                setState { state ->
                    state.copy(
                            isLoading = false ,
                            isSuccess = message.message ,
                            error = null ,
                            email = ""
                              )
                }
            }.onForbidden { message ->
                setState { state ->
                    state.copy(
                            isLoading = false ,
                            isSuccess = null,
                            error = message ,
                            email = ""
                              )
                }
            }.onNotFound { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isSuccess = null,
                            error = message,
                            email = ""
                              )
                }
            }.onUnprocessable { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isSuccess = null,
                            error = message,
                            email = ""
                              )
                }
            }.onServerError { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isSuccess = null,
                            error = message,
                            email = ""
                              )
                }
            }.onError { message ->
                setState { state ->
                    state.copy(
                            isLoading = false,
                            isSuccess = null,
                            error = "Something went wrong, please try again.",
                            email = ""
                              )
                }
            }
        }
    }

    private fun validateCredentials(): Boolean {
        val email = currentState.email
        val isValidEmail = AuthValidator.isValidEmail(email)

        setState { state ->
            state.copy(isValidEmail = isValidEmail)
        }
        return isValidEmail
    }

}
