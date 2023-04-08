package com.antonprokopov.albumsfeed.usecase

import com.antonprokopov.albumsfeed.data.api.ApiService
import com.antonprokopov.albumsfeed.data.models.AlbumDto
import com.antonprokopov.albumsfeed.data.models.ExtendedAlbumDto
import com.antonprokopov.albumsfeed.data.models.PhotoDto
import com.antonprokopov.albumsfeed.data.models.UserDto
import com.antonprokopov.albumsfeed.di.AlbumsFeedScope
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AlbumsFeedScope
class AlbumsUseCase @Inject constructor(private val apiService: ApiService) {

    suspend fun execute(): Flow<Resource<List<ExtendedAlbumDto>>> {
        return flow {
            emit(Resource.newLoading())

            val result = try {
                Resource.newSuccess(getExtendedAlbumsData())
            } catch (e: Exception) {
                Resource.newError()
            }

            emit(result)
        }
    }

    private suspend fun getExtendedAlbumsData(): List<ExtendedAlbumDto> {
        var users: List<UserDto>? = null
        var albums: List<AlbumDto>? = null
        var photos: List<PhotoDto>? = null
        val extendedAlbums = mutableListOf<ExtendedAlbumDto>()

        coroutineScope {
            launch {
                users = apiService.getUsers()
                albums = apiService.getAlbums()
                photos = apiService.getPhotos()
            }.join()

            albums?.forEach { album ->
                extendedAlbums.add(
                    ExtendedAlbumDto(
                        album = album,
                        user = users?.find { it.id == album.userId },
                        firstPhoto = photos?.firstOrNull { it.albumId == album.id }
                    )
                )
            }
        }

        return extendedAlbums
    }

}