package com.antonprokopov.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkResources {

    companion object {
        private const val CONNECT_TIMEOUT_SECONDS = 3L
        private const val WRITE_READ_TIMEOUT_SECONDS = 30L
    }

    internal fun createRetrofit(): Retrofit.Builder {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(WRITE_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
    }

}