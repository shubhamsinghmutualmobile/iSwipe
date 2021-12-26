package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.Either
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.network.utils.safeApiCall
import com.mutualmobile.iswipe.data.states.ResponseState
import io.ktor.client.request.get
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.KoinComponent

@ExperimentalSerializationApi
class YoutubeAPIImpl constructor(networkModule: NetworkModule) : YoutubeAPI, KoinComponent {
    private val networkClient = networkModule.getNetworkClient()

    override suspend fun getTrendingVideos(countryCode: String, apiKey: String, pageToken: String): ResponseState<YoutubeTrendingVideosResponse> {
        val response = safeApiCall {
            networkClient.get(NetworkUtils.getTrendingYoutubeVideosUrl(countryCode, apiKey, pageToken)) as YoutubeTrendingVideosResponse
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
