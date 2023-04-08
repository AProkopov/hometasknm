package com.antonprokopov.albumsfeed.di

import com.antonprokopov.albumsfeed.ui.AlbumsActivity
import com.antonprokopov.albumsfeed.ui.AlbumsFragment
import com.antonprokopov.core.di.ParentComponentProvider
import dagger.Subcomponent
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AlbumsFeedScope

@AlbumsFeedScope
@Subcomponent(modules = [AlbumsFeedModule::class])
interface AlbumsFeedComponent {
    fun inject(activity: AlbumsActivity)
    fun inject(fragment: AlbumsFragment)
}

interface AlbumsFeedParentComponent {
    fun getAlbumsFeedComponent(): AlbumsFeedComponent
}

object AlbumsFeedComponentHolder {

    private var component: AlbumsFeedComponent? = null
    private var usageCounter = 0

    fun initComponent(): AlbumsFeedComponent {
        if (component == null) {
            component = (ParentComponentProvider.appComponent as AlbumsFeedParentComponent).getAlbumsFeedComponent()
        }
        usageCounter++
        return component!!
    }

    fun getComponent() = component

    fun releaseComponent() {
        usageCounter--
        if (usageCounter <= 0) {
            usageCounter = 0
            component = null
        }
    }
}