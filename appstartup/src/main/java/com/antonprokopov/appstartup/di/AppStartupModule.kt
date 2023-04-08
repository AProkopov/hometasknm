package com.antonprokopov.appstartup.di

import androidx.lifecycle.ViewModelProvider
import com.antonprokopov.appstartup.viewmodel.SplashViewModel
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import dagger.Module
import dagger.Provides

@Module
class AppStartupModule {

    @AppStartupScope
    @Provides
    fun provideLifeCycleOwnerHolder(): ActivityLifecycleOwnerHolder = ActivityLifecycleOwnerHolder()

    @AppStartupScope
    @Provides
    fun provideSplashVm(activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder): SplashViewModel =
        ViewModelProvider(activityLifecycleOwnerHolder.viewModelStoreOwner).get(SplashViewModel::class.java)
}