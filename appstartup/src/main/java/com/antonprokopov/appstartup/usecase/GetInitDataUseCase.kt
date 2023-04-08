package com.antonprokopov.appstartup.usecase

import com.antonprokopov.appstartup.di.AppStartupScope
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AppStartupScope
class GetInitDataUseCase @Inject constructor() {

    companion object {
        private const val FAKE_APP_STARTUP_DATA_RESPONSE_DELAY_MILLIS = 1000L
    }

    /**
     * Here we simulate network request on application startup (getting initial data on splash screen)
    */
    suspend fun execute(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.newLoading())
            delay(FAKE_APP_STARTUP_DATA_RESPONSE_DELAY_MILLIS)
            emit(Resource.newSuccess(true))
        }
    }
}