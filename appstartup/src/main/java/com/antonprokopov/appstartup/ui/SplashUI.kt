package com.antonprokopov.appstartup.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import com.antonprokopov.appstartup.viewmodel.SplashVm
import javax.inject.Inject


class SplashUI @Inject constructor(private val feedRouter: AlbumsFeedRouter) {

    private lateinit var splashVm: SplashVm

    fun initialize(owner: Fragment) {
        splashVm = ViewModelProvider(owner).get(SplashVm::class.java)
        initSubscriptions(owner)
        getAppInitialData()
    }

    private fun initSubscriptions(owner: Fragment) {
        splashVm.initialDataLiveData.observe(
            owner,
            {
                feedRouter.openAlbumsFeed(owner.requireContext())
            } // doAction
        )
    }

    private fun getAppInitialData() {
        splashVm.getInitialData()
    }

}