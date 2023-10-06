package com.example.srmc.core.repository

import com.example.srmc.core.model.AuthCredential
import com.example.srmc.core.model.ForgotResult
import com.example.srmc.core.model.LoginResult
import com.example.srmc.core.model.RegisterResult
import javax.inject.Singleton

/**
 * Network Repository for user authorization of SRMC.
 */
@Singleton
interface AuthRepository
{
    /**
     * Register / Create a new user using [ username ] and [ password ]
     */
    suspend fun addUser(
            username : String,
            email : String,
            password : String,
            password_confirmation : String
    ) : Either<RegisterResult>

    /**
     * Sign ins a user using [ email ] and [ password ]
     */
    suspend fun getUserByEmailAndPassword(
            email : String,
            password : String
    ) : Either<AuthCredential>

    suspend fun forgotPassword(email : String) : Either<ForgotResult>
}