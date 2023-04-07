package com.antonprokopov.network.di

import com.antonprokopov.network.ApiService
import com.antonprokopov.network.NetworkResources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideNetworkResources(): NetworkResources = NetworkResources()

    @Provides
    @Singleton
    internal fun provideApiService(
        networkResources: NetworkResources
    ): ApiService {
        return networkResources.createRetrofit()
            .baseUrl("https://testurl")
            .build()
            .create(ApiService::class.java)
    }

}