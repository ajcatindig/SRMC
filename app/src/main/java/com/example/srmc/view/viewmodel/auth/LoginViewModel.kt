package com.example.srmc.view.viewmodel.auth

import com.example.srmc.view.state.auth.LoginState
import com.example.srmc.view.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.srmc.core.session.SessionManager
@HiltViewModel
class LoginViewModel @Inject constructor(
//        private val loginRepository : AuthRepository,
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
        TODO("Not yet implemented")
    }

}