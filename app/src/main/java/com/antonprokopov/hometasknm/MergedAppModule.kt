package com.antonprokopov.hometasknm

import com.antonprokopov.albumsfeed.di.AlbumsFeedModule
import com.antonprokopov.appstartup.di.AppStartupModule
import com.antonprokopov.appstartup.di.AppStartupUiModule
import dagger.Module

@Module(includes = [AlbumsFeedModule::class, AppStartupModule::class, AppStartupUiModule::class])
class MergedAppModule {
}