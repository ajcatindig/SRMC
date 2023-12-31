package com.example.srmc.core.session

import javax.inject.Singleton

@Singleton
interface SessionManager {
    fun saveToken(token : String?)
    fun getToken() : String?
    fun saveEmail(email : String?)
    fun getEmail() : String?
}