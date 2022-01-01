package com.mutualmobile.iswipe.android.view.screens.weather_screen.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.theme.WeatherScreenTypography

@Composable
fun WeatherFailureScreen(failureMsg: String) {

    val failureAnimaComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.weather_failure_animation))
    val failureAnimaAnimationProgress by animateLottieCompositionAsState(
        composition = failureAnimaComposition,
        iterations = LottieConstants.IterateForever
    )
    var isMsgExpanded by remember { mutableStateOf(false) }
    val arrowRotation by animateFloatAsState(targetValue = if (isMsgExpanded) 180f else 0f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = failureAnimaComposition,
                progress = failureAnimaAnimationProgress,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .aspectRatio(1f),
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Oh no! There's some error!",
                        style = WeatherScreenTypography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        "Click on the arrow to reveal the technical error message\nor\nSwipe from the top to retry",
                        style = WeatherScreenTypography.labelSmall,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    if (isMsgExpanded) {
                        Text(
                            failureMsg,
                            style = WeatherScreenTypography.labelSmall,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                IconButton(
                    onClick = { isMsgExpanded = !isMsgExpanded },
                    modifier = Modifier
                        .rotate(arrowRotation)
                        .align(Alignment.TopEnd)
                ) { Icon(Icons.Default.ArrowDropDown, null) }
            }
        }
    }
}
