package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState

interface WeatherAPI {
    @Throws(Exception::class)
    suspend fun getCurrentWeather(cityName: String = "delhi", apiKey: String = NetworkUtils.WEATHER_API_KEY): CurrentWeatherState
}
