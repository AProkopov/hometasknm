package com.antonprokopov.albumsfeed.ui.albumslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.antonprokopov.albumsfeed.data.models.ExtendedAlbumDto
import com.antonprokopov.albumsfeed.databinding.ItemAlbumBinding


class AlbumsAdapter(context: Context) : RecyclerView.Adapter<AlbumViewHolder>() {

    private var albumItems: List<ExtendedAlbumDto> = emptyList()
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = AlbumViewHolder(
        ItemAlbumBinding.inflate(inflater, parent, false)
    )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumItems[position])
    }

    override fun getItemCount() = albumItems.size

    fun setList(list: List<ExtendedAlbumDto>) {
        albumItems = ArrayList(list)
        notifyDataSetChanged()
    }
}