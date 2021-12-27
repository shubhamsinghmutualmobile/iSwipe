package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.YoutubeScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YoutubeMiniPlayer() {
    val coroutineScope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val screenHeightDp = with(LocalDensity.current) {
        (
            LocalConfiguration.current.screenHeightDp.toDp() - rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars
            ).calculateTopPadding() + 3.dp
            ).toPx()
    }
    val anchors = mapOf(0f to 0, screenHeightDp to 1)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = YoutubeScreen.DRAGGABLE_CARD_MIN_HEIGHT.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Vertical,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                reverseDirection = true
            )
            .height(swipeableState.offset.value.dp),
        shape = RoundedCornerShape(0),
        backgroundColor = Color.Transparent,
        onClick = {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    swipeableState.animateTo(1, tween(durationMillis = 500))
                }
            }
        },
        elevation = 0.dp
    ) {
        BackHandler(onBack = {
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    swipeableState.animateTo(0, tween(durationMillis = 500))
                }
            }
        })
        ExoPlayer()
    }
}
