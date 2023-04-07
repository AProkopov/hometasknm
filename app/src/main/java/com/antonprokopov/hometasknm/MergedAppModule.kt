package com.antonprokopov.hometasknm

import android.content.Context
import com.antonprokopov.albumsfeed.di.AlbumsFeedModule
import dagger.Module
import dagger.Provides

@Module(includes = [AlbumsFeedModule::class])
class MergedAppModule(private val appContext: Context) {
    @Provides
    internal fun provideContext(): Context {
        return appContext
    }
}