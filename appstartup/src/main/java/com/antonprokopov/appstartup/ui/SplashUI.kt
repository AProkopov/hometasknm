package com.antonprokopov.appstartup.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.antonprokopov.appstartup.viewmodel.SplashVm
import javax.inject.Inject


class SplashUI @Inject constructor() {

    lateinit var splashVm: SplashVm

    fun setLifecycleOwnerAndCreateViewModel(owner: ViewModelStoreOwner) {
        splashVm = ViewModelProvider(owner).get(SplashVm::class.java)
    }

}