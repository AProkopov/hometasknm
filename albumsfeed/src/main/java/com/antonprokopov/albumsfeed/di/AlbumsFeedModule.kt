package com.antonprokopov.albumsfeed.di

import com.antonprokopov.albumsfeed.ui.AlbumsFeedRouterImpl
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AlbumsFeedModule {

    @Provides
    fun provideMyOffersRouter(): AlbumsFeedRouter = AlbumsFeedRouterImpl()

}