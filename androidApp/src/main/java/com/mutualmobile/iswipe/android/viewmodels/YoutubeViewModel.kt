package com.mutualmobile.iswipe.android.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.local.YoutubeChannelBasic
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
const val YOUTUBE_CHANNEL_BASE_URL = "https://www.youtube.com/channel/"
private const val TAG = "YoutubeViewModel"

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

    private val _currentYoutubeChannelBasic: MutableStateFlow<YoutubeChannelBasic?> = MutableStateFlow(null)
    val currentYoutubeChannelBasic: StateFlow<YoutubeChannelBasic?> = _currentYoutubeChannelBasic.asStateFlow()

    private val _isMiniPlayerLoading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isMiniPlayerLoading: StateFlow<Boolean> = _isMiniPlayerLoading.asStateFlow()

    init {
        getCurrentYoutubeResponse()
        getStreamLinkFromYouTubeId()
    }

    fun getCurrentYoutubeResponse(getNextPage: Boolean = false) {
        try {
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
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    private fun updateListOfYoutubeVideos() {
        try {
            viewModelScope.launch {
                if (_currentYoutubeResponse.value is ResponseState.Success) {
                    (_currentYoutubeResponse.value as ResponseState.Success).data.items?.let { nnItemList ->
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
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    fun addNewItemsToList() = getCurrentYoutubeResponse(_currentYoutubeResponse.value is ResponseState.Success)

    fun clearYoutubeList() {
        try {
            viewModelScope.launch {
                _listOfYoutubeVideos.emit(mutableListOf())
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    fun toggleIsVideoPlaying(isVideoPlaying: Boolean) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _isVideoPlaying.emit(!isVideoPlaying)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    fun setIsCardExpanded(isCardExpanded: Boolean) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _isCardExpanded.emit(isCardExpanded)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    fun toggleIsCardTouched(isCardTouched: Boolean) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _isCardTouched.emit(!isCardTouched)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    fun updateCurrentSelectedVideoItem(videoItem: Item?) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _currentSelectedVideoItem.emit(videoItem)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    private fun getStreamLinkFromYouTubeId() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _currentSelectedVideoItem.collectLatest { item ->
                        item?.let { nnItem ->
                            val completeVideoUrl = YOUTUBE_BASE_URL + nnItem.videoLinkEndPart
                            val completeChannelUrl = YOUTUBE_CHANNEL_BASE_URL + nnItem.snippet?.channelId

                            val youtubeService = YoutubeService(0)

                            val linkDetails = youtubeService.getStreamExtractor(completeVideoUrl).apply { fetchPage() }
                            val channelDetails = youtubeService.getChannelExtractor(completeChannelUrl).apply { fetchPage() }

                            val channelToEmit = YoutubeChannelBasic(
                                streamLink = linkDetails.videoStreams[2].url,
                                channelAvatarUrl = linkDetails.uploaderAvatarUrl,
                                subscriberCount = channelDetails.subscriberCount.toString(),
                                channelName = channelDetails.name
                            )

                            _currentYoutubeChannelBasic.emit(channelToEmit)
                        } ?: _currentYoutubeChannelBasic.emit(null)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.localizedMessage.orEmpty())
        }
    }

    fun setMiniPlayerLoading(isLoading: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _isMiniPlayerLoading.emit(isLoading)
        }
    }
}
