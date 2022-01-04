package com.mutualmobile.iswipe.viewmodels

import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item

expect class YoutubeViewModel(youtubeApi: YoutubeAPI) {
    fun getCurrentYoutubeResponse(getNextPage: Boolean = false)
    fun updateListOfYoutubeVideos()
    fun addNewItemsToList()
    fun clearYoutubeList()
    fun toggleIsVideoPlaying(isVideoPlaying: Boolean)
    fun setIsCardExpanded(isCardExpanded: Boolean)
    fun toggleIsCardTouched(isCardTouched: Boolean)
    fun updateCurrentSelectedVideoItem(videoItem: Item?)
    fun getStreamLinkFromYouTubeId()
    fun setMiniPlayerLoading(isLoading: Boolean)
}
