package com.mutualmobile.iswipe.data.network.utils

object NetworkUtils {
    const val WEATHER_API_KEY = "6bf5e395a1bce8e879a6175a06006663"

    fun getCurrentWeatherApiUrl(city: String = "delhi", apiKey: String = WEATHER_API_KEY): String {
        return "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey"
    }
}
