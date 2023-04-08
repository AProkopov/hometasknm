package com.antonprokopov.appstartup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonprokopov.appstartup.di.AppStartupComponentHolder
import com.antonprokopov.appstartup.usecase.GetInitDataUseCase
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel : ViewModel() {

    @Inject
    lateinit var getInitDataUseCase: GetInitDataUseCase

    init {
        AppStartupComponentHolder.getComponent()?.inject(this)
    }

    val initialDataLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loadingStateLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorStateLiveData: MutableLiveData<Resource.ErrorDesc> = MutableLiveData<Resource.ErrorDesc>()

    fun getInitialData() {
        viewModelScope.launch {
            getInitDataUseCase.execute()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    initialDataLiveData.value = false
                }
                .collect {
                    loadingStateLiveData.value = it is Resource.Loading

                    when (it) {
                        is Resource.Success -> initialDataLiveData.value = it.data
                        is Resource.Error -> errorStateLiveData.value = it.desc
                    }
                }
        }
    }

}