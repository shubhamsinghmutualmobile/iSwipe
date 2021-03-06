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
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.YoutubeScreen
import com.mutualmobile.iswipe.viewmodels.YoutubeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.get

object YoutubeMiniPlayer {
    const val SWIPE_ANIMATION_DURATION = 500
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YoutubeMiniPlayer(
    youtubeViewModel: YoutubeViewModel = get(),
    swipeableState: SwipeableState<Int>,
    videoStreamLink: String
) {
    val isCardTouched by youtubeViewModel.isCardTouched.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val screenHeightDp = with(LocalDensity.current) {
        (
            LocalConfiguration.current.screenHeightDp.toDp() - rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars
            ).calculateTopPadding() + 3.dp
            ).toPx()
    }
    val anchors = mapOf(0f to 0, screenHeightDp to 1)

    with(youtubeViewModel) {
        setIsCardExpanded(swipeableState.currentValue == 1)
        setMiniPlayerLoading(isLoading = false)
    }

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
        backgroundColor = MaterialTheme.colorScheme.surface,
        onClick = {
            youtubeViewModel.toggleIsCardTouched(isCardTouched)
            coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    swipeableState.animateTo(1, tween(durationMillis = YoutubeMiniPlayer.SWIPE_ANIMATION_DURATION))
                }
            }
        },
        elevation = 0.dp,
        indication = null
    ) {
        BackHandler(onBack = collapseYoutubePlayer(coroutineScope, swipeableState))
        ExoPlayer(videoUrl = videoStreamLink, onBack = collapseYoutubePlayer(coroutineScope, swipeableState))
    }
}

@ExperimentalMaterialApi
private fun collapseYoutubePlayer(
    coroutineScope: CoroutineScope,
    swipeableState: SwipeableState<Int>
): () -> Unit = {
    coroutineScope.launch {
        withContext(Dispatchers.IO) {
            swipeableState.animateTo(0, tween(durationMillis = YoutubeMiniPlayer.SWIPE_ANIMATION_DURATION))
        }
    }
}
