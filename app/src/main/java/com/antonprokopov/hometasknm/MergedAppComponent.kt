package com.antonprokopov.hometasknm

import com.antonprokopov.albumsfeed.di.AlbumsFeedParentComponent
import com.antonprokopov.appstartup.di.AppStartupParentComponent
import com.antonprokopov.appstartup.di.AppStartupUiModule
import com.antonprokopov.core.di.ComponentParent
import com.antonprokopov.network.di.NetworkModule
import com.antonprokopov.network.di.NetworkParentComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MergedAppModule::class, AppStartupUiModule::class, NetworkModule::class])
interface MergedAppComponent: AlbumsFeedParentComponent, AppStartupParentComponent, ComponentParent, NetworkParentComponent {
}