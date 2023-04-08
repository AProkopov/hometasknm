package com.antonprokopov.hometasknm

import com.antonprokopov.albumsfeed.di.AlbumsFeedParentComponent
import com.antonprokopov.appstartup.di.AppStartupParentComponent
import com.antonprokopov.core.di.ComponentParent
import com.antonprokopov.network.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MergedAppModule::class, NetworkModule::class])
interface MergedAppComponent: AppStartupParentComponent, AlbumsFeedParentComponent, ComponentParent