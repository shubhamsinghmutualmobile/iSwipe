package com.mutualmobile.iswipe.data.network.models.weather.weather_current

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String?,
    val id: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val type: Int?
)
