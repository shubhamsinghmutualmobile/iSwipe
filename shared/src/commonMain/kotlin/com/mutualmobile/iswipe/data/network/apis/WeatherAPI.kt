package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.network.models.weather.weather_current.CurrentWeatherResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState

interface WeatherAPI {
    @Throws(Exception::class)
    suspend fun getCurrentWeather(cityName: String = "delhi", apiKey: String = NetworkUtils.WEATHER_API_KEY): ResponseState<CurrentWeatherResponse>
}
