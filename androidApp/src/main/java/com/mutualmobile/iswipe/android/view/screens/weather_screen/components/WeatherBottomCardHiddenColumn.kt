package com.mutualmobile.iswipe.android.view.screens.weather_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.view.screens.weather_screen.utils.hpaToBar
import com.mutualmobile.iswipe.android.view.screens.weather_screen.utils.kelvinToCelsius
import com.mutualmobile.iswipe.data.network.models.weather.weather_current.CurrentWeatherResponse
import com.mutualmobile.iswipe.data.network.utils.millisToTextualDayDate

@Composable
fun WeatherBottomCardHiddenColumn(
    isCardExpanded: Boolean,
    weatherItem: CurrentWeatherResponse,
    bottomHiddenListState: LazyListState
) {
    AnimatedVisibility(
        visible = isCardExpanded,
        enter = slideInVertically(initialOffsetY = { it / 2 }),
        exit = slideOutVertically(targetOffsetY = { it / 2 })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.5f)
        ) {
            val hiddenInfoRows by remember {
                val currentDay = weatherItem.dt?.millisToTextualDayDate(weatherItem.timezone)?.first
                val currentDate = weatherItem.dt?.millisToTextualDayDate(weatherItem.timezone)?.second
                mutableStateOf(
                    listOf(
                        Pair("Minimum temperature", "${weatherItem.main?.temp_min?.kelvinToCelsius()}C"),
                        Pair("Maximum temperature", "${weatherItem.main?.temp_max?.kelvinToCelsius()}C"),
                        Pair("Atmospheric pressure", weatherItem.main?.pressure?.hpaToBar()),
                        Pair("Clouds", weatherItem.clouds?.all.toString()),
                        Pair("Day", currentDay),
                        Pair("Date", currentDate),
                    )
                )
            }
            LazyColumn(state = bottomHiddenListState) {
                items(count = hiddenInfoRows.size) { index ->
                    WeatherBottomCardHiddenTextRow(title = hiddenInfoRows[index].first, value = hiddenInfoRows[index].second.orEmpty())
                }
                item {
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}
