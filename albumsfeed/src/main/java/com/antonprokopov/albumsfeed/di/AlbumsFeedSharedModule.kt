package com.antonprokopov.albumsfeed.di

import com.antonprokopov.albumsfeed.ui.AlbumsFeedRouterImpl
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import dagger.Module
import dagger.Provides

@Module
class AlbumsFeedSharedModule {

    @Provides
    fun provideMyOffersRouter(): AlbumsFeedRouter = AlbumsFeedRouterImpl()
}