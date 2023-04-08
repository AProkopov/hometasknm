package com.antonprokopov.albumsfeed.data.models

/**
 * Here is no need to mark fields with @SerializedName annotation.
 * This Dto is not transferred with network and we don't need to protect ourselves from
 * parsing issues in release builds caused with obfuscation.
 */
data class ExtendedAlbumDto(
    val album: AlbumDto?,
    val user: UserDto?,
    val firstPhoto: PhotoDto?,
)