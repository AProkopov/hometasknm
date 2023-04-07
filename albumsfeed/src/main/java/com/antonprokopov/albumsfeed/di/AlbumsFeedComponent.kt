package com.antonprokopov.albumsfeed.di

import dagger.Subcomponent

@Subcomponent(modules = [AlbumsFeedModule::class])
interface AlbumsFeedComponent {

}

interface AlbumsFeedParentComponent {
    fun getAlbumsFeedComponent(): AlbumsFeedComponent
}