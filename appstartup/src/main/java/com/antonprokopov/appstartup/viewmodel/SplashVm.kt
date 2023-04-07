package com.antonprokopov.appstartup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonprokopov.appstartup.di.AppStartupComponentHolder
import com.antonprokopov.appstartup.usecase.GetInitDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class SplashVm : ViewModel() {

    @Inject
    lateinit var getInitDataUseCase: GetInitDataUseCase

    init {
        AppStartupComponentHolder.getComponent()?.inject(this)
    }

    val initialDataLiveData: MutableLiveData<Any> = MutableLiveData<Any>()


    fun getInitialData() {
//        viewModelScope.launch {
//            getInitDataUseCase.execute()
//                .flowOn(Dispatchers.IO)
//                .catch { e ->
//                    //handle exception
//                }
//                .collect {
//                    //handle result
//                }
//        }
    }

}