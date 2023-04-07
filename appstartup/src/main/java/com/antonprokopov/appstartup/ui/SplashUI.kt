package com.antonprokopov.appstartup.ui

import android.content.Context
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
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


    private fun initSubscriptions() {
        splashVm.initialDataLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                feedRouter.openAlbumsFeed(context)
            } // doAction
        )
    }

    private fun getAppInitialData() {
        splashVm.getInitialData()
    }

}