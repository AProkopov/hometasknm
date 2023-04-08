package com.antonprokopov.albumsfeed.di

import com.antonprokopov.albumsfeed.data.api.ApiService
import com.antonprokopov.albumsfeed.ui.AlbumsFeedRouterImpl
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import com.antonprokopov.network.NetworkResources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AlbumsFeedModule {

    @Provides
    fun provideMyOffersRouter(): AlbumsFeedRouter = AlbumsFeedRouterImpl()

    @Provides
    @Singleton
    internal fun provideApiService(
        networkResources: NetworkResources
    ): ApiService {
        return networkResources.createRetrofit()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .build()
            .create(ApiService::class.java)
    }

}