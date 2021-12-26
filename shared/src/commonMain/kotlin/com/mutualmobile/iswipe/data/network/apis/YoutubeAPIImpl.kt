package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.Either
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.network.utils.safeApiCall
import com.mutualmobile.iswipe.data.states.ResponseState
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent

class YoutubeAPIImpl constructor(networkModule: NetworkModule) : YoutubeAPI, KoinComponent {
    private val networkClient = networkModule.getNetworkClient()

    override suspend fun getTrendingVideos(countryCode: String, apiKey: String): ResponseState<YoutubeTrendingVideosResponse> {
        val response = safeApiCall {
            networkClient.get(NetworkUtils.getTrendingYoutubeVideos(countryCode, apiKey)) as YoutubeTrendingVideosResponse
        }
        return when (response) {
            is Either.Type -> {
                ResponseState.Success(data = response.data)
            }
            is Either.Error -> {
                ResponseState.Failure(errorMsg = response.errorMsg)
            }
        }
    }
}
