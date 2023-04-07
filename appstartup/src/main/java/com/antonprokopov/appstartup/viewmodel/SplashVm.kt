package com.antonprokopov.appstartup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonprokopov.appstartup.di.AppStartupComponentHolder
import com.antonprokopov.appstartup.usecase.GetInitDataUseCase
import javax.inject.Inject


class SplashVm : ViewModel() {

    @Inject
    lateinit var getInitDataUseCase: GetInitDataUseCase

    init {
        AppStartupComponentHolder.getComponent()?.inject(this)
    }

    val initialDataLiveData: MutableLiveData<Any> = MutableLiveData<Any>()


    fun getInitialData() {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }

}