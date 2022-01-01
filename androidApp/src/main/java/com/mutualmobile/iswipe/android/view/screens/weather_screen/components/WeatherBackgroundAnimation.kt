package com.mutualmobile.iswipe.android.view.screens.weather_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mutualmobile.iswipe.android.R

@Composable
fun WeatherBackgroundAnimation(animationRef: Int?) {
    AnimatedVisibility(
        visible = animationRef != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val backgroundWeatherAnimComposition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                animationRef ?: R.raw.weather_clear_animation
            )
        )
        val backgroundWeatherAnimAnimationProgress by animateLottieCompositionAsState(
            composition = backgroundWeatherAnimComposition,
            iterations = LottieConstants.IterateForever,
            speed = 0.25f
        )

        LottieAnimation(
            composition = backgroundWeatherAnimComposition,
            progress = backgroundWeatherAnimAnimationProgress,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}
