package com.antonprokopov.albumsfeed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonprokopov.albumsfeed.di.AlbumsFeedComponentHolder
import com.antonprokopov.albumsfeed.usecase.AlbumsUseCase
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumsViewModel: ViewModel() {

    @Inject
    lateinit var albumsUseCase: AlbumsUseCase

    init {
        AlbumsFeedComponentHolder.getComponent()?.inject(this)
    }

    val albumsDataLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val loadingStateLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorStateLiveData: MutableLiveData<Resource.ErrorDesc> = MutableLiveData<Resource.ErrorDesc>()

    fun getAlbumPreviews() {
        viewModelScope.launch {
            albumsUseCase.execute()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    albumsDataLiveData.value = false
                }
                .collect {
                    loadingStateLiveData.value = it is Resource.Loading

                    when (it) {
                        is Resource.Success -> albumsDataLiveData.value = it.data
                        is Resource.Error -> errorStateLiveData.value = it.desc
                    }
                }
        }
    }
}