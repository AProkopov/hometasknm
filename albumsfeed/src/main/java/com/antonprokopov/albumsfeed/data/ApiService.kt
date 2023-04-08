package com.antonprokopov.albumsfeed.data

import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getAlbums(): List<Any>

}