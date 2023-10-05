package com.example.srmc.view.viewmodel.auth

import com.example.srmc.utils.validator.AuthValidator
import com.example.srmc.view.state.auth.ForgotState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotViewModel @Inject constructor(
) : BaseViewModel<ForgotState>(initialState = ForgotState())
{
    fun setEmail(email : String) {
        setState { state -> state.copy(email = email) }
    }

    fun sendEmail(){

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
