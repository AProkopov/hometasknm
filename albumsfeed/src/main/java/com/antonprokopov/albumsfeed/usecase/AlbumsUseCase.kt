package com.antonprokopov.albumsfeed.usecase

import com.antonprokopov.albumsfeed.data.api.ApiService
import com.antonprokopov.albumsfeed.di.AlbumsFeedScope
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AlbumsFeedScope
class AlbumsUseCase @Inject constructor(private val apiService: ApiService) {

    suspend fun execute(): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.newLoading())
            kotlinx.coroutines.delay(500L)
            emit(Resource.newSuccess(true))
        }
    }

}