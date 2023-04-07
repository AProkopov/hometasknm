package com.antonprokopov.albumsfeed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.antonprokopov.albumsfeed.databinding.FragmentAlbumsFeedBinding
import com.antonprokopov.core.ui.BaseViewBindingFragment

class AlbumsFragment : BaseViewBindingFragment<FragmentAlbumsFeedBinding>() {

    companion object {
        const val TAG = "AlbumsFragment"
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachedToRoot: Boolean,
        savedInstanceState: Bundle?
    ): FragmentAlbumsFeedBinding {
        return FragmentAlbumsFeedBinding.inflate(inflater, container, attachedToRoot)
    }
}