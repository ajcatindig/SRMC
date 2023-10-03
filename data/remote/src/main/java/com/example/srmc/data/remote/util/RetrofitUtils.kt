package com.example.srmc.data.remote.util

import com.example.srmc.core.utils.fromJson
import retrofit2.Response
/**
 * Retrofit only gives generic response body when stastus is Successful.
 * This extension will also parse error body and will give generic response.
 */
inline fun <reified T> Response<T>.getResponse() : T {
    val responseBody = body()
    return if (this.isSuccessful && responseBody != null) {
        responseBody
    } else {
        fromJson<T>(errorBody()!!.string())!!
    }
}