package com.antonprokopov.appstartup.ui

import android.app.Activity
import android.content.Context
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import com.antonprokopov.appstartup.databinding.FragmentSplashBinding
import com.antonprokopov.appstartup.viewmodel.SplashVm
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import com.antonprokopov.core.ui.ViewBindingUi
import javax.inject.Inject


class SplashUI @Inject constructor(
    private val activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder,
    private val splashVm: SplashVm,
    private val feedRouter: AlbumsFeedRouter,
    private val context: Context
    ): ViewBindingUi<FragmentSplashBinding>() {

    init {
        initSubscriptions()
        getAppInitialData()
    }

    private fun initSubscriptions() {
        splashVm.initialDataLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                navigateAndCloseCurrentActivity()
            }
        )

        splashVm.loadingStateLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                /**
                 * Here we can show loading state or run animations.
                 * But our current splash screen is static.
                 */
            }
        )

        splashVm.errorStateLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                /**
                 * In case of failed application initial data fetching we can route
                 * to any specific screen (login screen or some non-auth screen).
                 * In this test application we don't use any real initialization data,
                 * then we will be routed to the same screen as in success case.
                */
                navigateAndCloseCurrentActivity()
            }
        )
    }

    private fun navigateAndCloseCurrentActivity() {
        feedRouter.openAlbumsFeed(context)
        (activityLifecycleOwnerHolder.lifecycleOwner as Activity).finish()
    }

    private fun getAppInitialData() {
        splashVm.getInitialData()
    }

}