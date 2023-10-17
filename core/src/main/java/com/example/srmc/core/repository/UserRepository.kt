package com.example.srmc.core.repository

import com.example.srmc.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface UserRepository
{
    fun getCurrentUser() : Flow<Either<User>>
}