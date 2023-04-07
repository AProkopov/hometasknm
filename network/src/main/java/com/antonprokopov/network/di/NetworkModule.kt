package com.antonprokopov.network.di

import com.antonprokopov.network.ApiService
import com.antonprokopov.network.NetworkResources
import com.antonprokopov.network.TestClassOne
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

//    @Singleton
//    @Provides
//    internal fun provideOkHttpClient(
//    ): OkHttpClient {
//        //here we can also add interceptors and apply cookies handling
//        return OkHttpClient.Builder()
//            .connectTimeout(3, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//    }

    @Provides
    internal fun getTestClass(): TestClassOne = TestClassOne()

//    @Provides
//    internal fun provideRetrofit(): Retrofit.Builder {
//        val httpClient = OkHttpClient.Builder()
//            .connectTimeout(3, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(httpClient)
//    }

    @Provides
    internal fun provideNetworkResources(): NetworkResources = NetworkResources()

    @Provides
    internal fun provideApiService(
        networkResources: NetworkResources
    ): ApiService {
        return networkResources.createRetrofit()
            .baseUrl("https://testurl")
            .build()
            .create(ApiService::class.java)
    }

}