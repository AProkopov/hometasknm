package com.antonprokopov.albumsfeed.ui

import android.content.Context
import com.antonprokopov.albumsfeed.R
import com.antonprokopov.albumsfeed.databinding.FragmentAlbumsFeedBinding
import com.antonprokopov.albumsfeed.viewmodel.AlbumsViewModel
import com.antonprokopov.core.extensions.gone
import com.antonprokopov.core.extensions.setVisibleOrGone
import com.antonprokopov.core.extensions.visible
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import com.antonprokopov.core.ui.EmptyStateView
import com.antonprokopov.core.ui.ViewBindingUi
import javax.inject.Inject

class AlbumsUi  @Inject constructor(
    private val activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder,
    private val albumsVm: AlbumsViewModel,
    private val context: Context
): ViewBindingUi<FragmentAlbumsFeedBinding>() {

    init {
        initSubscriptions()
        getAlbums()
    }

    private fun initSubscriptions() {
        albumsVm.albumsDataLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                if (it.isEmpty()) {
                    showEmptyState()
                } else {
                    showAlbums()
                }
            }
        )

        albumsVm.loadingStateLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                showLoadingState(it)
            }
        )

        albumsVm.errorStateLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                showErrorState()
            }
        )
    }

    private fun showErrorState() {
        fragmentViewBinding?.apply {
            emptyStateView.setState(EmptyStateView.EmptyState.ERROR, ::getAlbums)
            emptyStateView.visible()
        }
    }

    private fun showEmptyState() {
        fragmentViewBinding?.apply {
            emptyStateView.setState(EmptyStateView.EmptyState.EMPTY, ::getAlbums, R.string.albumsfeed_no_albums_text)
            emptyStateView.visible()
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        fragmentViewBinding?.apply {
            emptyStateView.gone()
            loadingView.setVisibleOrGone(isLoading)
        }
    }

    private fun showAlbums() {
        fragmentViewBinding?.emptyStateView.gone()
    }

    private fun getAlbums() {
        albumsVm.getAlbumPreviews()
    }
}