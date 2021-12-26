package com.mutualmobile.iswipe.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class YoutubeViewModel constructor(
    private val youtubeApi: YoutubeAPI
) : ViewModel() {
    private var _currentYoutubeResponse: MutableStateFlow<ResponseState<YoutubeTrendingVideosResponse>> = MutableStateFlow(ResponseState.Empty)
    val currentYoutubeResponse: StateFlow<ResponseState<YoutubeTrendingVideosResponse>> = _currentYoutubeResponse

    init {
        getCurrentYoutubeResponse()
    }

    fun getCurrentYoutubeResponse() {
        viewModelScope.launch {
            _currentYoutubeResponse.emit(ResponseState.Loading)
            when (val response = youtubeApi.getTrendingVideos()) {
                is ResponseState.Success -> {
                    _currentYoutubeResponse.emit(ResponseState.Success(data = response.data))
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
}
