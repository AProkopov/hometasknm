package com.antonprokopov.albumsfeed.ui

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antonprokopov.albumsfeed.R
import com.antonprokopov.albumsfeed.data.models.ExtendedAlbumDto
import com.antonprokopov.albumsfeed.databinding.FragmentAlbumsFeedBinding
import com.antonprokopov.albumsfeed.ui.albumslist.AlbumsAdapter
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
    }

    fun initUI() {
        fragmentViewBinding?.apply {
            rvAlbums.adapter = AlbumsAdapter(context)
            rvAlbums.layoutManager = LinearLayoutManager(context)
            swipeRefreshLayoutAlbums.setOnRefreshListener(::getAlbums)
            emptyStateView.setRetryCallback(::getAlbums)

            setDecorationToAlbumsList(rvAlbums)
        }

        getAlbums()
    }

    private fun initSubscriptions() {
        albumsVm.albumsDataLiveData.observe(
            activityLifecycleOwnerHolder.lifecycleOwner,
            {
                if (it.isEmpty()) {
                    showEmptyState()
                } else {
                    showAlbums(it)
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
            emptyStateView.setState(EmptyStateView.EmptyState.ERROR)
            emptyStateView.visible()
            swipeRefreshLayoutAlbums.gone()
        }
    }

    private fun showEmptyState() {
        fragmentViewBinding?.apply {
            emptyStateView.setState(EmptyStateView.EmptyState.EMPTY, R.string.albumsfeed_no_albums_text)
            emptyStateView.visible()
            swipeRefreshLayoutAlbums.gone()
        }
    }

    private fun showLoadingState(isLoading: Boolean) {
        fragmentViewBinding?.apply {
            emptyStateView.gone()
            loadingView.setVisibleOrGone(isLoading)
            swipeRefreshLayoutAlbums.isRefreshing = false
        }
    }

    private fun showAlbums(albums: List<ExtendedAlbumDto>) {
        fragmentViewBinding?.apply {
            emptyStateView.gone()
            swipeRefreshLayoutAlbums.visible()
            (rvAlbums.adapter as? AlbumsAdapter)?.setList(albums)
        }
    }

    private fun getAlbums() {
        albumsVm.getAlbumPreviews()
    }

    private fun setDecorationToAlbumsList(recyclerView: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.getContext(),
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}