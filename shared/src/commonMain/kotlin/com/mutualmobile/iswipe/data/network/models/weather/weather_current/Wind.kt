package com.mutualmobile.iswipe.data.network.models.weather.weather_current

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int?,
    val speed: Double?
)
