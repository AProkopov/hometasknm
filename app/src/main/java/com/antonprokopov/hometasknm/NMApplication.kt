package com.antonprokopov.hometasknm

import android.app.Application
import com.antonprokopov.core.di.ParentComponentProvider

class NMApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        ParentComponentProvider.appComponent = createComponent()
    }

    private fun createComponent(): MergedAppComponent {
        val component = DaggerMergedAppComponent.builder()
            .mergedAppModule(MergedAppModule(applicationContext))
            .build()
        return component

    }
}