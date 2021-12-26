package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState

interface YoutubeAPI {
    @Throws(Exception::class)
    suspend fun getTrendingVideos(
        countryCode: String = "IN",
        apiKey: String = NetworkUtils.YOUTUBE_DATA_API_KEY,
        pageToken: String = ""
    ): ResponseState<YoutubeTrendingVideosResponse>
}
