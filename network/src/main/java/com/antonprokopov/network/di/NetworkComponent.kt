package com.antonprokopov.network.di

import dagger.Subcomponent

@Subcomponent(modules = [NetworkModule::class])
interface NetworkComponent

interface NetworkParentComponent {
    fun getNetworkComponent(): NetworkComponent
}