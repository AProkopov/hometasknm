package com.antonprokopov.appstartup.usecase

import com.antonprokopov.appstartup.di.AppStartupScope
import com.antonprokopov.network.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@AppStartupScope
class GetInitDataUseCase @Inject constructor(
    private val apiService: ApiService
    ) {

//    fun execute(): Flow<Any> {
//
//    }



}