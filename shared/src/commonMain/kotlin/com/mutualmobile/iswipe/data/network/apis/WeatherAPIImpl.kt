package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.models.weather.CurrentWeatherResponse
import com.mutualmobile.iswipe.data.network.utils.Either
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.network.utils.safeApiCall
import com.mutualmobile.iswipe.data.states.ResponseState
import io.ktor.client.request.get
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.KoinComponent

@ExperimentalSerializationApi
class WeatherAPIImpl constructor(
    networkModule: NetworkModule
) : WeatherAPI, KoinComponent {
    private val networkClient = networkModule.getNetworkClient()

    override suspend fun getCurrentWeather(cityName: String, apiKey: String): ResponseState<CurrentWeatherResponse> {
        val response = safeApiCall {
            networkClient.get(NetworkUtils.getCurrentWeatherApiUrl()) as CurrentWeatherResponse
        }
        return when (response) {
            is Either.Type -> {
                ResponseState.Success(data = response.data)
            }
            is Either.Error -> {
                ResponseState.Failure(errorMsg = response.errorMsg)
            }
        }
    }
}
