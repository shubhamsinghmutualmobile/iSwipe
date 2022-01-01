package com.mutualmobile.iswipe.android.view.screens.weather_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.statusBarsPadding
import com.mutualmobile.iswipe.android.view.screens.weather_screen.utils.getWeatherBackground
import com.mutualmobile.iswipe.android.view.screens.weather_screen.utils.kelvinToCelsius
import com.mutualmobile.iswipe.android.view.screens.weather_screen.utils.layout90Rotated
import com.mutualmobile.iswipe.android.view.theme.WeatherScreenTypography
import com.mutualmobile.iswipe.data.network.models.weather.weather_current.CurrentWeatherResponse

private object WeatherSuccessScreen {
    const val TemperatureYOffset = -64
    const val HorizontalPadding = 24
    const val SpacerPadding = HorizontalPadding.div(3)
}

@Composable
fun WeatherSuccessScreen(weatherItem: CurrentWeatherResponse) {
    weatherItem.weather?.get(0)?.icon?.let { nnIconString ->
        val weatherBackgroundItem by remember { mutableStateOf(nnIconString.getWeatherBackground()) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = weatherBackgroundItem.first),
                contentDescription = "Weather Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.8f
            )
            WeatherBackgroundAnimation(weatherBackgroundItem.third)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = WeatherSuccessScreen.HorizontalPadding.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(WeatherSuccessScreen.SpacerPadding.dp)
                )
                Text(
                    weatherItem.name.orEmpty(),
                    style = WeatherScreenTypography.headlineMedium
                )
                Text(
                    weatherItem.main?.temp?.kelvinToCelsius().orEmpty(),
                    style = WeatherScreenTypography.headlineLarge,
                    modifier = Modifier.absoluteOffset(y = WeatherSuccessScreen.TemperatureYOffset.dp)
                )
            }
            Text(
                weatherItem.weather?.get(0)?.description?.capitalize().orEmpty(),
                style = WeatherScreenTypography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .rotate(-90f)
                    .layout90Rotated()
                    .statusBarsPadding()
                    .padding(bottom = 16.dp)
            )
            // This is just to enable swipe to refresh for the whole page
            LazyColumn(modifier = Modifier.fillMaxSize()) {}
            WeatherBottomExpandableCard(weatherItem)
        }
    }
}

@Composable
fun WeatherBottomCardText(value: String, title: String, animationRef: Int) {
    val iconAnimComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animationRef))
    val backgroundWeatherAnimAnimationProgress by animateLottieCompositionAsState(
        composition = iconAnimComposition,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
        LottieAnimation(
            composition = iconAnimComposition,
            progress = backgroundWeatherAnimAnimationProgress,
            modifier = Modifier
                .background(color = Color.White.copy(alpha = 1f), shape = CircleShape)
                .size(32.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )
        Text(value, style = WeatherScreenTypography.displayMedium)
        Text(title, style = WeatherScreenTypography.displaySmall)
    }
}
