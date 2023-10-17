package com.example.srmc.di

import com.example.srmc.core.utils.moshi
import com.example.srmc.data.remote.Constant
import com.example.srmc.data.remote.api.AuthService
import com.example.srmc.data.remote.api.DoctorService
import com.example.srmc.data.remote.api.UserService
import com.example.srmc.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val baseRetrofitBuilder : Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())

    private val okHttpClientBuilder : OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)



    @Provides
    fun provideAuthService() : AuthService
    {
        return baseRetrofitBuilder
                .client(okHttpClientBuilder.build())
                .build()
                .create(AuthService::class.java)
    }

    @Provides
    fun provideDoctorService(authInterceptor : AuthInterceptor) : DoctorService
    {
        return baseRetrofitBuilder
                .client(okHttpClientBuilder.addInterceptor(authInterceptor).build())
                .build()
                .create(DoctorService::class.java)
    }

    @Provides
    fun provideUserService(authInterceptor : AuthInterceptor) : UserService
    {
        return baseRetrofitBuilder
                .client(okHttpClientBuilder.addInterceptor(authInterceptor).build())
                .build()
                .create(UserService::class.java)
    }

}
