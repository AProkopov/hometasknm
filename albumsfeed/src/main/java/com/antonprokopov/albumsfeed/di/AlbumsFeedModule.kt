package com.antonprokopov.albumsfeed.di

import androidx.lifecycle.ViewModelProvider
import com.antonprokopov.albumsfeed.data.api.ApiService
import com.antonprokopov.albumsfeed.viewmodel.AlbumsViewModel
import com.antonprokopov.core.ui.ActivityLifecycleOwnerHolder
import com.antonprokopov.network.NetworkResources
import dagger.Module
import dagger.Provides

@Module
class AlbumsFeedModule {

    @AlbumsFeedScope
    @Provides
    internal fun provideApiService(
        networkResources: NetworkResources
    ): ApiService {
        return networkResources.createRetrofit()
            .baseUrl("https://jsonplaceholder.typicode.com") //TODO get this url from config to support release and debug versions
            .build()
            .create(ApiService::class.java)
    }

    @AlbumsFeedScope
    @Provides
    fun provideLifeCycleOwnerHolder(): ActivityLifecycleOwnerHolder = ActivityLifecycleOwnerHolder()

    @AlbumsFeedScope
    @Provides
    fun provideSplashVm(activityLifecycleOwnerHolder: ActivityLifecycleOwnerHolder): AlbumsViewModel =
        ViewModelProvider(activityLifecycleOwnerHolder.viewModelStoreOwner).get(AlbumsViewModel::class.java)

}