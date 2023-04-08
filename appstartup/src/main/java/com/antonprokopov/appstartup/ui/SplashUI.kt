package com.antonprokopov.appstartup.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import com.antonprokopov.appstartup.databinding.FragmentSplashBinding
import com.antonprokopov.appstartup.viewmodel.SplashVm
import javax.inject.Inject


class SplashUI @Inject constructor(
    private val activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder,
    private val splashVm: SplashVm,
    private val feedRouter: AlbumsFeedRouter,
    private val context: Context
    ) {

    init {
        initSubscriptions()
        getAppInitialData()
    }

    private var fragmentViewBinding: FragmentSplashBinding? = null

    fun setViewBinding(fragmentViewBinding: FragmentSplashBinding) {
        this.fragmentViewBinding = fragmentViewBinding
    }

    private fun initSubscriptions() {
        splashVm.initialDataLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                feedRouter.openAlbumsFeed(context)
            }
        )

        splashVm.loadingStateLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                //show loading
                Log.d("AZAZA", it.toString())
            }
        )

        splashVm.errorStateLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                /**
                 * In case of failed application initial data fetching we can route
                 * to any specific screen (login screen or some non-auth screen).
                 * In this test application we don't use any real initialization data,
                 * then we will be routed to the same screen as in success case
                */
                feedRouter.openAlbumsFeed(context)
            }
        )
    }

    private fun getAppInitialData() {
        splashVm.getInitialData()
    }

}