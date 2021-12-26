package com.mutualmobile.iswipe.data.network.models.youtube_trending_videos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Localized(
    @SerialName("description")
    val description: String?,
    @SerialName("title")
    val title: String?
)
