package com.antonprokopov.albumsfeed.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonprokopov.albumsfeed.databinding.FragmentAlbumsFeedBinding
import com.antonprokopov.albumsfeed.di.AlbumsFeedComponentHolder
import com.antonprokopov.core.ui.BaseViewBindingFragment
import javax.inject.Inject

class AlbumsFragment : BaseViewBindingFragment<FragmentAlbumsFeedBinding>() {

    companion object {
        const val TAG = "AlbumsFragment"
    }

    @Inject
    lateinit var albumsUi: AlbumsUi

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachedToRoot: Boolean,
        savedInstanceState: Bundle?
    ): FragmentAlbumsFeedBinding {
        return FragmentAlbumsFeedBinding.inflate(inflater, container, attachedToRoot)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
            .also { albumsUi.fragmentViewBinding = this.viewBinding }
            .also { albumsUi.initUI() }
    }

    override fun onAttach(context: Context) {
        AlbumsFeedComponentHolder.initComponent().inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        AlbumsFeedComponentHolder.releaseComponent()
        super.onDetach()
    }
}