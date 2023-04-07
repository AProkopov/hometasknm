package com.antonprokopov.hometasknm

import com.antonprokopov.albumsfeed.di.AlbumsFeedParentComponent
import com.antonprokopov.appstartup.di.AppStartupParentComponent
import com.antonprokopov.appstartup.di.AppStartupUiModule
import com.antonprokopov.core.di.ComponentParent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MergedAppModule::class, AppStartupUiModule::class])
interface MergedAppComponent: AlbumsFeedParentComponent, AppStartupParentComponent, ComponentParent {
}