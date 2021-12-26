package com.mutualmobile.iswipe.android.view.screens.weather_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mutualmobile.iswipe.android.viewmodels.WeatherViewModel
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState
import org.koin.androidx.compose.get

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = get()
) {
    val currentWeather = weatherViewModel.currentWeather.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = currentWeather.value is CurrentWeatherState.Loading)

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        state = isRefreshing,
        onRefresh = { weatherViewModel.getCurrentWeather() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
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
}
