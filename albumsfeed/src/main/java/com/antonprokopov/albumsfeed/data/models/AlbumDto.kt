package com.antonprokopov.albumsfeed.data.models

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String? = null
)