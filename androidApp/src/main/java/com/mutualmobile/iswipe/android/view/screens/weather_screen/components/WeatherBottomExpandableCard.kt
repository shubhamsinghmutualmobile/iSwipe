package com.mutualmobile.iswipe.android.view.screens.weather_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.screens.weather_screen.utils.meterToKilometre
import com.mutualmobile.iswipe.data.network.models.weather.weather_current.CurrentWeatherResponse
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.WeatherBottomExpandableCard(weatherItem: CurrentWeatherResponse) {
    var isCardExpanded by remember { mutableStateOf(false) }
    val cardBackgroundAlpha by animateFloatAsState(targetValue = if (isCardExpanded) 0.5f else 0.1f)
    val bottomHiddenListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.BottomCenter)) {
        AnimatedVisibility(
            visible = !isCardExpanded,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Text(
                text = "Tap on the card to reveal more details",
                color = Color.White,
                fontStyle = FontStyle.Italic
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 16.dp)
                .animateContentSize(),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, Color.White),
            backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = cardBackgroundAlpha),
            elevation = 0.dp,
            onClick = {
                isCardExpanded = !isCardExpanded
                if (!isCardExpanded) {
                    coroutineScope.launch { bottomHiddenListState.scrollToItem(0) }
                }
            },
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    WeatherBottomCardText("${weatherItem.main?.humidity?.toString()}%", "Humidity", R.raw.weather_humidity_animation)
                    WeatherBottomCardText(
                        weatherItem.visibility?.meterToKilometre().orEmpty(),
                        "Visibility",
                        R.raw.weather_visibility_animation
                    )
                    WeatherBottomCardText("${weatherItem.wind?.speed} m/s", "Wind Speed", R.raw.weather_wind_speed_animation)
                }
                WeatherBottomCardHiddenColumn(isCardExpanded, weatherItem, bottomHiddenListState)
            }
        }
    }
}
