package com.mutualmobile.iswipe.android.view.screens.weather_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mutualmobile.iswipe.android.viewmodels.WeatherViewModel
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState
import org.koin.androidx.compose.get

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = get()
) {
    val currentWeather = weatherViewModel.currentWeather.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = when (val result = currentWeather.value) {
                is CurrentWeatherState.Empty -> {
                    ""
                }
                is CurrentWeatherState.Loading -> {
                    "Loading weather..."
                }
                is CurrentWeatherState.Success -> {
                    result.data.toString()
                }
                is CurrentWeatherState.Failure -> {
                    result.errorMsg
                }
            }
        )
    }
}
