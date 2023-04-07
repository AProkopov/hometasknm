package com.antonprokopov.appstartup.di

import com.antonprokopov.appstartup.ui.SplashActivity
import com.antonprokopov.appstartup.ui.SplashFragment
import com.antonprokopov.appstartup.viewmodel.SplashVm
import com.antonprokopov.core.di.ParentComponentProvider
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppStartupScope

@AppStartupScope
@Subcomponent(modules = [AppStartupModule::class, AppStartupUiModule::class])
interface AppStartupComponent {
    fun inject(fragment: SplashFragment)
    fun inject(splashVm: SplashVm)
    fun inject(activity: SplashActivity)
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