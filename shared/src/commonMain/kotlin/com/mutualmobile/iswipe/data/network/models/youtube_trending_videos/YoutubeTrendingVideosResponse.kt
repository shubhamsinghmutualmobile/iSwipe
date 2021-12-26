package com.mutualmobile.iswipe.data.network.models.youtube_trending_videos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YoutubeTrendingVideosResponse(
    @SerialName("etag")
    val etag: String?,
    @SerialName("items")
    val items: List<Item>?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("nextPageToken")
    val nextPageToken: String?,
    @SerialName("pageInfo")
    val pageInfo: PageInfo?
)
