package com.example.srmc.repository

import android.util.Log
import com.example.srmc.core.model.User
import com.example.srmc.core.repository.Either
import com.example.srmc.core.repository.UserRepository
import com.example.srmc.data.remote.api.UserService
import com.example.srmc.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject internal  constructor(
        private val userService : UserService
) : UserRepository
{
    override fun getCurrentUser() : Flow<Either<User>> = flow {
        val userResponse = userService.getUserProfile().getResponse()

        val status = when (userResponse.status) {
            200 -> Either.success(userResponse.data)
            else -> Either.error(userResponse.message!!)
        }
        emit(status)
        Log.d("Data", "${userResponse.data}")
    }.catch {
        Log.e("UserRepositoryImpl", "catch ${it.message!!}")
        emit(Either.error("An unknown error occurred"))
    }

}