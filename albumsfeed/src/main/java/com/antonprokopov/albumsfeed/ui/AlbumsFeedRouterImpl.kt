package com.antonprokopov.albumsfeed.ui

import android.content.Context
import com.antonprokopov.albumsfeedapi.route.AlbumsFeedRouter

class AlbumsFeedRouterImpl: AlbumsFeedRouter {
    override fun openAlbumsFeed(context: Context) {
        context.startActivity(AlbumsActivity.newIntent(context))
    }
}