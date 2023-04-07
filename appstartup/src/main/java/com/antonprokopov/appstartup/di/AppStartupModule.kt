package com.antonprokopov.appstartup.di

import androidx.lifecycle.ViewModelProvider
import com.antonprokopov.appstartup.ui.ActivityLifecycleOwnerHolder
import com.antonprokopov.appstartup.viewmodel.SplashVm
import dagger.Module
import dagger.Provides

@Module
class AppStartupModule {

    @AppStartupScope
    @Provides
    fun provideLifeCycleOwnerHolder(): ActivityLifecycleOwnerHolder = ActivityLifecycleOwnerHolder()

    @AppStartupScope
    @Provides
    fun provideSplashVm(activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder): SplashVm =
        ViewModelProvider(activityLifecycleOwnerHolder.viewModelStoreOwner).get(SplashVm::class.java)
}