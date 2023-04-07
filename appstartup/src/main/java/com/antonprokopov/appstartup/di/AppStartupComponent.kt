package com.antonprokopov.appstartup.di

import com.antonprokopov.appstartup.ui.SplashFragment
import com.antonprokopov.core.di.ParentComponentProvider
import dagger.Subcomponent

@Subcomponent(modules = [AppStartupModule::class, AppStartupModule::class])
interface AppStartupComponent {
    fun inject(fragment: SplashFragment)
}

interface AppStartupParentComponent {
    fun getAppStartupComponent(): AppStartupComponent
}

object AppStartupComponentHolder {

    private var component: AppStartupComponent? = null
    private var usageCounter = 0

    fun initComponent(): AppStartupComponent {
        if (component == null) {
            component = (ParentComponentProvider.appComponent as AppStartupParentComponent).getAppStartupComponent()
        }
        usageCounter++
        return component!!
    }

    fun getComponent() = component

    fun releaseComponent() {
        usageCounter--
        if (usageCounter <= 0) {
            usageCounter = 0
            component = null
        }
    }
}