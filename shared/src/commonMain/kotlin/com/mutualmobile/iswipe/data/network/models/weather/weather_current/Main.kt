package com.mutualmobile.iswipe.data.network.models.weather.weather_current

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val feels_like: Double?,
    val humidity: Int?,
    val pressure: Int?,
    val temp: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)
