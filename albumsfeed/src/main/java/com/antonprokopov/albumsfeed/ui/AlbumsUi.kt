package com.antonprokopov.albumsfeed.ui

import android.content.Context
import com.antonprokopov.albumsfeed.databinding.FragmentAlbumsFeedBinding
import com.antonprokopov.albumsfeed.viewmodel.AlbumsViewModel
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import com.antonprokopov.core.ui.ViewBindingUi
import javax.inject.Inject

class AlbumsUi  @Inject constructor(
    private val activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder,
    private val albumsVm: AlbumsViewModel,
    private val context: Context
): ViewBindingUi<FragmentAlbumsFeedBinding>() {
}