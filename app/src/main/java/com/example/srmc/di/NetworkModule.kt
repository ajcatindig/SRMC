package com.example.srmc.di

import com.example.srmc.core.utils.moshi
import com.example.srmc.data.remote.Constant
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
    private val baseRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constant.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    private val okHttpClientBuilder: OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)

//    @Provides
//    fun provideNotyService(authInterceptor: AuthInterceptor): NotyService {
//        return baseRetrofitBuilder
//                .client(okHttpClientBuilder.addInterceptor(authInterceptor).build())
//                .build()
//                .create(NotyService::class.java)
//    }
//
//    @Provides
//    fun provideNotyAuthService(): NotyAuthService {
//        return baseRetrofitBuilder
//                .client(okHttpClientBuilder.build())
//                .build()
//                .create(NotyAuthService::class.java)
//    }
}