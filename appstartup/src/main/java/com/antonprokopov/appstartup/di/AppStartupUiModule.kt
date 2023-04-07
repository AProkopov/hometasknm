package com.antonprokopov.appstartup.di

import com.antonprokopov.appstartup.ui.SplashActivity
import com.antonprokopov.appstartup.ui.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppStartupUiModule {

    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun provideSplashFragment() : SplashFragment
}