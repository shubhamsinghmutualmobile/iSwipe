package com.mutualmobile.iswipe.data.network.models.weather

import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    val lat: Double?,
    val lon: Double?
)
