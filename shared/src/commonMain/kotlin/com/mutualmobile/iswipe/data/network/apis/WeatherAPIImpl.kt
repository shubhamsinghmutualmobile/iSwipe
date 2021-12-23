package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.models.weather.CurrentWeatherResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherAPIImpl : WeatherAPI, KoinComponent {
    private val networkModule: NetworkModule by inject()
    private val networkClient = networkModule.getNetworkClient()

    override suspend fun getCurrentWeather(cityName: String, apiKey: String): CurrentWeatherResponse {
        return networkClient.get(NetworkUtils.getCurrentWeatherApiUrl())
    }
}
