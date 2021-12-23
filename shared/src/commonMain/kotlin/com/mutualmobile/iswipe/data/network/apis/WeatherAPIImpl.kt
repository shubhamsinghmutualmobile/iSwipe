package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.models.weather.CurrentWeatherResponse
import com.mutualmobile.iswipe.data.network.utils.Either
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.network.utils.safeApiCall
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState
import io.ktor.client.request.get
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherAPIImpl : WeatherAPI, KoinComponent {
    private val networkModule: NetworkModule by inject()
    private val networkClient = networkModule.getNetworkClient()

    override suspend fun getCurrentWeather(cityName: String, apiKey: String): CurrentWeatherState {
        val response = safeApiCall {
            networkClient.get(NetworkUtils.getCurrentWeatherApiUrl()) as CurrentWeatherResponse
        }
        return when (response) {
            is Either.Type -> {
                CurrentWeatherState.Success(data = response.type)
            }
            is Either.Error -> {
                CurrentWeatherState.Failure(errorMsg = response.errorMsg)
            }
        }
    }
}
