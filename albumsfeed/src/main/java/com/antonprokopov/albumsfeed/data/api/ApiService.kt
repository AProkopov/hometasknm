package com.antonprokopov.albumsfeed.data.api

import com.antonprokopov.albumsfeed.data.models.AlbumDto
import com.antonprokopov.albumsfeed.data.models.PhotoDto
import com.antonprokopov.albumsfeed.data.models.UserDto
import retrofit2.http.GET

interface ApiService {

    @GET("/users")
    suspend fun getUsers(): List<UserDto>

    @GET("/photos")
    suspend fun getPhotos(): List<PhotoDto>

    @GET("/albums")
    suspend fun getAlbums(): List<AlbumDto>
}