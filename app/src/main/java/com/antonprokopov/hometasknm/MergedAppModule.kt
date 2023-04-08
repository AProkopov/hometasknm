package com.antonprokopov.hometasknm

import android.content.Context
import com.antonprokopov.albumsfeed.di.AlbumsFeedSharedModule
import dagger.Module
import dagger.Provides

@Module(includes = [AlbumsFeedSharedModule::class])
class MergedAppModule(private val appContext: Context) {
    @Provides
    internal fun provideContext(): Context {
        return appContext
    }
}