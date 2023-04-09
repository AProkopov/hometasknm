package com.antonprokopov.albumsfeed.ui.albumslist

import androidx.recyclerview.widget.RecyclerView
import com.antonprokopov.albumsfeed.data.models.ExtendedAlbumDto
import com.antonprokopov.albumsfeed.databinding.ItemAlbumBinding
import com.antonprokopov.core.utils.PicassoHelper

class AlbumViewHolder(itemViewBinding: ItemAlbumBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {

    private val albumThumbnail = itemViewBinding.albumThumbnail
    private val titlePhoto = itemViewBinding.titlePhoto
    private val albumName = itemViewBinding.albumName
    private val userName= itemViewBinding.userName

    fun bind(item: ExtendedAlbumDto) {
        item.firstPhoto?.thumbnailUrl?.let { PicassoHelper.loadImageSimply(albumThumbnail, it) }
        titlePhoto.text = item.firstPhoto?.title
        albumName.text = item.album?.title
        userName.text = item.user?.username
    }
}