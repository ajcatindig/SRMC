package com.example.srmc.view.viewmodel.auth

import com.example.srmc.view.state.auth.RegisterState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.srmc.utils.validator.AuthValidator
@HiltViewModel
class RegisterViewModel @Inject constructor()
    : BaseViewModel<RegisterState>(initialState = RegisterState())
{
    fun setUsername(username : String) {
        setState { state -> state.copy(username = username) }
    }

    fun setEmail(email : String) {
        setState { state -> state.copy(email = email) }
    }

    fun setPassword(password : String) {
        setState { state -> state.copy(password = password) }
    }

    fun setConfirmPassword(password : String) {
        setState { state -> state.copy(confirmPassword = password) }
    }

    fun register() {

    }

    private fun validateCredentials(): Boolean {
        val username = currentState.username
        val email = currentState.email
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword

        val isValidUsername = AuthValidator.isValidUsername(username)
        val isValidEmail = AuthValidator.isValidEmail(email)
        val isValidPassword = AuthValidator.isValidPassword(password)
        val arePasswordAndConfirmPasswordSame = AuthValidator.isPasswordAndConfirmPasswordSame(
                password,
                confirmPassword)

        setState { state ->
            state.copy(
                    isValidUsername = isValidUsername,
                    isValidEmail = isValidEmail,
                    isValidPassword = isValidPassword,
                    isValidConfirmPassword = arePasswordAndConfirmPasswordSame
                      )
        }

        return isValidUsername && isValidEmail && isValidPassword && arePasswordAndConfirmPasswordSame
    }
}