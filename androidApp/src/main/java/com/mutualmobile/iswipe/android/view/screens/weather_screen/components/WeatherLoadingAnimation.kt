package com.mutualmobile.iswipe.android.view.screens.weather_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.theme.WeatherScreenTypography

private object WeatherLoadingAnimation {
    const val LoadingTextNegativeYOffset = -64
}

@Composable
fun WeatherLoadingAnimation() {
    val loadingComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.weather_loading_animation))
    val animationProgress by animateLottieCompositionAsState(composition = loadingComposition, iterations = LottieConstants.IterateForever)
    val loadingText by remember {
        mutableStateOf(
            listOf(
                "Asking the God of Thunder about his status...",
                "When life gives you a rainy day, PLAY IN THE PUDDLES!",
                "Zeus is busy with Kratos for now, so we're measuring the weather on our own...",
                "Jim and Pam are kissing, it must be RAINING outside..."
            ).random()
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = loadingComposition,
            progress = animationProgress,
            modifier = Modifier
                .fillMaxSize(0.4f)
                .wrapContentHeight(),
            alignment = Alignment.BottomCenter,
            contentScale = ContentScale.Crop,
        )
        Text(
            loadingText,
            textAlign = TextAlign.Center,
            style = WeatherScreenTypography.labelMedium,
            modifier = Modifier
                .offset(y = WeatherLoadingAnimation.LoadingTextNegativeYOffset.dp)
                .fillMaxWidth(0.5f)
        )
    }
}

@Preview
@Composable
fun WeatherLoadingAnimationPreview() {
    WeatherLoadingAnimation()
}
