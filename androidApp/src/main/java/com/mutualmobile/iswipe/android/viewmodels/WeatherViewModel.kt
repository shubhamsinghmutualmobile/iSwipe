package com.mutualmobile.iswipe.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutualmobile.iswipe.data.network.apis.WeatherAPI
import com.mutualmobile.iswipe.data.network.utils.NetworkUtils
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel constructor(
    private val weatherAPI: WeatherAPI
) : ViewModel() {
    private var _currentWeather: MutableStateFlow<CurrentWeatherState> = MutableStateFlow(CurrentWeatherState.Empty)
    val currentWeather: StateFlow<CurrentWeatherState> = _currentWeather

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            _currentWeather.emit(CurrentWeatherState.Loading)
            when (val response = weatherAPI.getCurrentWeather()) {
                is CurrentWeatherState.Success -> {
                    _currentWeather.emit(CurrentWeatherState.Success(data = response.data))
                }
                is CurrentWeatherState.Failure -> {
                    _currentWeather.emit(CurrentWeatherState.Failure(errorMsg = response.errorMsg))
                }
                else -> {

                    _currentWeather.emit(CurrentWeatherState.Failure(errorMsg = NetworkUtils.GENERIC_ERROR_MSG))
                }
            }
        }
    }
}
