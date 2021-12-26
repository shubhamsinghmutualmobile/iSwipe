package com.mutualmobile.iswipe.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class YoutubeViewModel constructor(
    private val youtubeApi: YoutubeAPI
) : ViewModel() {
    private var _currentYoutubeResponse: MutableStateFlow<ResponseState<YoutubeTrendingVideosResponse>> = MutableStateFlow(ResponseState.Empty)
    val currentYoutubeResponse: StateFlow<ResponseState<YoutubeTrendingVideosResponse>> = _currentYoutubeResponse

    private val _listOfYoutubeVideos: MutableStateFlow<MutableList<Item>> = MutableStateFlow(mutableListOf())
    val listOfYoutubeVideos: StateFlow<MutableList<Item>> = _listOfYoutubeVideos.asStateFlow()

    init {
        getCurrentYoutubeResponse()
    }

    fun getCurrentYoutubeResponse(getNextPage: Boolean = false) {
        viewModelScope.launch {
            if (!getNextPage) {
                _currentYoutubeResponse.emit(ResponseState.Loading)
            }
            when (
                val response = youtubeApi.getTrendingVideos(
                    pageToken = if (getNextPage) {
                        (_currentYoutubeResponse.value as ResponseState.Success).data.nextPageToken.orEmpty()
                    } else {
                        ""
                    }
                )
            ) {
                is ResponseState.Success -> {
                    _currentYoutubeResponse.emit(ResponseState.Success(data = response.data))
                    updateListOfYoutubeVideos()
                }
                is ResponseState.Failure -> {
                    _currentYoutubeResponse.emit(ResponseState.Failure(errorMsg = response.errorMsg))
                }
                else -> {
                    _currentYoutubeResponse.emit(ResponseState.Failure(errorMsg = NetworkUtils.GENERIC_ERROR_MSG))
                }
            }
        }
    }

    private fun updateListOfYoutubeVideos() {
        viewModelScope.launch {
            currentYoutubeResponse.collect { responseState ->
                if (responseState is ResponseState.Success) {
                    responseState.data.items?.let { nnItemList ->
                        nnItemList.forEach { item ->
                            if (!_listOfYoutubeVideos.value.contains(item)) {
                                _listOfYoutubeVideos.value.add(item)
                            }
                        }
                    }
                }
            }
        }
    }

    fun addNewItemsToList() = getCurrentYoutubeResponse(_currentYoutubeResponse.value is ResponseState.Success)

    fun clearYoutubeList() {
        viewModelScope.launch {
            _listOfYoutubeVideos.emit(mutableListOf())
        }
    }
}
