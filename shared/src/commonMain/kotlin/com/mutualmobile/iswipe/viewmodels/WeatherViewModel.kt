package com.mutualmobile.iswipe.viewmodels

import com.mutualmobile.iswipe.data.network.apis.WeatherAPI

expect class WeatherViewModel(weatherAPI: WeatherAPI) {
    fun getCurrentWeather()
}
