package com.antonprokopov.hometasknm

import com.antonprokopov.albumsfeed.di.AlbumsFeedModule
import dagger.Module

@Module(includes = [AlbumsFeedModule::class])
class MergedAppModule {
}