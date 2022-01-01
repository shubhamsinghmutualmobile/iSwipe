package com.mutualmobile.iswipe.android.view.screens.weather_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mutualmobile.iswipe.android.view.screens.weather_screen.components.WeatherFailureScreen
import com.mutualmobile.iswipe.android.view.screens.weather_screen.components.WeatherLoadingAnimation
import com.mutualmobile.iswipe.android.view.screens.weather_screen.components.WeatherSuccessScreen
import com.mutualmobile.iswipe.android.viewmodels.WeatherViewModel
import com.mutualmobile.iswipe.data.states.ResponseState
import org.koin.androidx.compose.get

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel = get()
) {
    val currentWeather = weatherViewModel.currentWeather.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = currentWeather.value is ResponseState.Loading)
    val insets = LocalWindowInsets.current.statusBars.top
    val topInsetDp = with(LocalDensity.current) { insets.toDp() }

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = isRefreshing,
        onRefresh = { weatherViewModel.getCurrentWeather() },
        indicatorPadding = PaddingValues(top = topInsetDp)
    ) {
        AnimatedContent(targetState = currentWeather.value) { targetState ->
            when (targetState) {
                is ResponseState.Empty -> {}
                is ResponseState.Loading -> {
                    WeatherLoadingAnimation()
                }
                is ResponseState.Success -> {
                    WeatherSuccessScreen(weatherItem = targetState.data)
                }
                is ResponseState.Failure -> {
                    WeatherFailureScreen(failureMsg = targetState.errorMsg)
                }
            }
        }
    }
}
