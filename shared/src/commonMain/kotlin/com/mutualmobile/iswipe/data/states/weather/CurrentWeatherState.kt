package com.mutualmobile.iswipe.data.states.weather

import com.mutualmobile.iswipe.data.network.models.weather.CurrentWeatherResponse

sealed class CurrentWeatherState {
    object Empty : CurrentWeatherState()
    object Loading : CurrentWeatherState()
    class Success(val data: CurrentWeatherResponse) : CurrentWeatherState()
    class Failure(val errorMsg: String) : CurrentWeatherState()
}
