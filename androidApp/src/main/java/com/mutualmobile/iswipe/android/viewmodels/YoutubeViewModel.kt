package com.mutualmobile.iswipe.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.services.youtube.YoutubeService

const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="

class YoutubeViewModel constructor(
    private val youtubeApi: YoutubeAPI
) : ViewModel() {
    private val _currentYoutubeResponse: MutableStateFlow<ResponseState<YoutubeTrendingVideosResponse>> = MutableStateFlow(ResponseState.Empty)
    val currentYoutubeResponse: StateFlow<ResponseState<YoutubeTrendingVideosResponse>> = _currentYoutubeResponse

    private val _listOfYoutubeVideos: MutableStateFlow<MutableList<Item>> = MutableStateFlow(mutableListOf())
    val listOfYoutubeVideos: StateFlow<MutableList<Item>> = _listOfYoutubeVideos.asStateFlow()

    private val _isVideoPlaying: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isVideoPlaying: StateFlow<Boolean> = _isVideoPlaying.asStateFlow()

    private val _isCardExpanded: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isCardExpanded: StateFlow<Boolean> = _isCardExpanded.asStateFlow()

    private val _isCardTouched: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isCardTouched: StateFlow<Boolean> = _isCardTouched.asStateFlow()

    private val _currentSelectedVideoItem: MutableStateFlow<Item?> = MutableStateFlow(null)
    val currentSelectedVideoItem: StateFlow<Item?> = _currentSelectedVideoItem.asStateFlow()

    private val _currentVideoStreamLink: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentVideoStreamLink: StateFlow<String?> = _currentVideoStreamLink.asStateFlow()

    init {
        getCurrentYoutubeResponse()
        getStreamLinkFromYouTubeId()
    }

    fun getCurrentYoutubeResponse(getNextPage: Boolean = false) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
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
                        if (_listOfYoutubeVideos.value.isEmpty()) {
                            _currentYoutubeResponse.emit(ResponseState.Failure(errorMsg = response.errorMsg))
                        }
                    }
                    else -> {
                        _currentYoutubeResponse.emit(ResponseState.Failure(errorMsg = NetworkUtils.GENERIC_ERROR_MSG))
                    }
                }
            }
        }
    }

    private fun updateListOfYoutubeVideos() {
        viewModelScope.launch {
            if (_currentYoutubeResponse.value is ResponseState.Success) {
                (_currentYoutubeResponse.value as ResponseState.Success).data.items?.let { nnItemList ->
//                    _listOfYoutubeVideos.value.addAll(
//                        nnItemList.filter { item ->
//                            !_listOfYoutubeVideos.value.contains(item)
//                        }
//                    )
//                    _listOfYoutubeVideos.value.addAll(
//                        nnItemList.minus(_listOfYoutubeVideos.value.toHashSet())
//                    )
                    withContext(Dispatchers.IO) {
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

    fun toggleIsVideoPlaying(isVideoPlaying: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isVideoPlaying.emit(!isVideoPlaying)
            }
        }
    }

    fun setIsCardExpanded(isCardExpanded: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isCardExpanded.emit(isCardExpanded)
            }
        }
    }

    fun toggleIsCardTouched(isCardTouched: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isCardTouched.emit(!isCardTouched)
            }
        }
    }

    fun updateCurrentSelectedVideoItem(videoItem: Item?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _currentSelectedVideoItem.emit(videoItem)
            }
        }
    }

    private fun getStreamLinkFromYouTubeId() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _currentSelectedVideoItem.collectLatest { item ->
                    item?.let { nnItem ->
                        val completeUrl = YOUTUBE_BASE_URL + nnItem.videoLinkEndPart
                        _currentVideoStreamLink.emit(
                            YoutubeService(0).getStreamExtractor(completeUrl).apply {
                                fetchPage()
                            }.videoStreams[2].url
                        )
                    } ?: _currentVideoStreamLink.emit(null)
                }
            }
        }
    }
}
