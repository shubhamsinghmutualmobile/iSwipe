package com.mutualmobile.iswipe.android.view.screens.weather_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mutualmobile.iswipe.android.view.screens.weather_screen.components.WeatherFailureScreen
import com.mutualmobile.iswipe.android.view.screens.weather_screen.components.WeatherLoadingAnimation
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

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        state = isRefreshing,
        onRefresh = { weatherViewModel.getCurrentWeather() }
    ) {

        AnimatedContent(targetState = currentWeather.value) { targetState ->
            when (targetState) {
                is ResponseState.Empty -> {
                    Text("")
                }
                is ResponseState.Loading -> {
                    WeatherLoadingAnimation()
                }
                is ResponseState.Success -> {
                    Text(targetState.data.toString())
                }
                is ResponseState.Failure -> {
                    WeatherFailureScreen(failureMsg = targetState.errorMsg)
                }
            }
        }
    }
}
