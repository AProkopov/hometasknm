package com.antonprokopov.albumsfeed.usecase

import android.util.Log
import com.antonprokopov.albumsfeed.data.api.ApiService
import com.antonprokopov.albumsfeed.data.models.AlbumDto
import com.antonprokopov.albumsfeed.data.models.ExtendedAlbumDto
import com.antonprokopov.albumsfeed.data.models.PhotoDto
import com.antonprokopov.albumsfeed.data.models.UserDto
import com.antonprokopov.albumsfeed.di.AlbumsFeedScope
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    @ExperimentalCoroutinesApi
    private suspend fun getExtendedAlbumsData(): List<ExtendedAlbumDto> {
        var users: Deferred<List<UserDto>?>
        var albums: Deferred<List<AlbumDto>?>
        var photos: Deferred<List<PhotoDto>?>
        val extendedAlbums = mutableListOf<ExtendedAlbumDto>()

        coroutineScope {
            users = async { apiService.getUsers() }
            albums = async { apiService.getAlbums() }
            photos = async { apiService.getPhotos() }
            awaitAll(users, albums, photos)

            albums.getCompleted()?.forEach { album ->
                extendedAlbums.add(
                    ExtendedAlbumDto(
                        album = album,
                        user = users.getCompleted()?.find { it.id == album.userId },
                        firstPhoto = photos.getCompleted()?.firstOrNull { it.albumId == album.id }
                    )
                )
            }
        }

        return extendedAlbums
    }
}