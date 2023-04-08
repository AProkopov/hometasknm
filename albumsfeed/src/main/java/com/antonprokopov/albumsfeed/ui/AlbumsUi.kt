package com.antonprokopov.albumsfeed.ui

import android.content.Context
import com.antonprokopov.albumsfeed.databinding.FragmentAlbumsFeedBinding
import com.antonprokopov.albumsfeed.viewmodel.AlbumsViewModel
import com.antonprokopov.core.extensions.gone
import com.antonprokopov.core.extensions.setVisibleOrGone
import com.antonprokopov.core.extensions.visible
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import com.antonprokopov.core.ui.ViewBindingUi
import javax.inject.Inject

class AlbumsUi  @Inject constructor(
    private val activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder,
    private val albumsVm: AlbumsViewModel,
    private val context: Context
): ViewBindingUi<FragmentAlbumsFeedBinding>() {

    init {
        initSubscriptions()
        getAppInitialData()
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
        fragmentViewBinding?.emptyStateContainer.visible()
    }

    private fun showEmptyState() {
        fragmentViewBinding?.emptyStateContainer.visible()
    }

    private fun showLoadingState(isLoading: Boolean) {
        fragmentViewBinding?.emptyStateContainer.gone()
        fragmentViewBinding?.loadingView.setVisibleOrGone(isLoading)
    }

    private fun showAlbums() {
        fragmentViewBinding?.emptyStateContainer.gone()
    }

    private fun getAppInitialData() {
        albumsVm.getAlbumPreviews()
    }
}