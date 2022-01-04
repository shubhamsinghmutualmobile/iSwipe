package com.mutualmobile.iswipe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.iswipe.data.network.apis.WeatherAPI
import com.mutualmobile.iswipe.data.network.models.weather.weather_current.CurrentWeatherResponse
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

actual class WeatherViewModel actual constructor(private val weatherAPI: WeatherAPI) : ViewModel() {
    private var _currentWeather: MutableStateFlow<ResponseState<CurrentWeatherResponse>> = MutableStateFlow(ResponseState.Empty)
    val currentWeather: StateFlow<ResponseState<CurrentWeatherResponse>> = _currentWeather.asStateFlow()

    init {
        getCurrentWeather()
    }

    actual fun getCurrentWeather() {
        viewModelScope.launch {
            _currentWeather.emit(ResponseState.Loading)
            when (val response = weatherAPI.getCurrentWeather()) {
                is ResponseState.Success -> {
                    _currentWeather.emit(ResponseState.Success(data = response.data))
                }
                is ResponseState.Failure -> {
                    _currentWeather.emit(ResponseState.Failure(errorMsg = response.errorMsg))
                }
                else -> {

                    _currentWeather.emit(ResponseState.Failure(errorMsg = NetworkUtils.GENERIC_ERROR_MSG))
                }
            }
        }
    }
}
