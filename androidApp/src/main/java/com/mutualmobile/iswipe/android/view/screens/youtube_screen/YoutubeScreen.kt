package com.mutualmobile.iswipe.android.view.screens.youtube_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.components.YoutubeMiniPlayer
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.components.YoutubeTopBar
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.components.YoutubeVideoList
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import kotlin.math.roundToInt

object YoutubeScreen {
    const val DRAGGABLE_CARD_MIN_HEIGHT = 64
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun YoutubeScreen(
    youtubeViewModel: YoutubeViewModel = get()
) {
    val currentYoutubeChannelBasic by youtubeViewModel.currentYoutubeChannelBasic.collectAsState()
    val isCardExpanded by youtubeViewModel.isCardExpanded.collectAsState()
    val response = youtubeViewModel.currentYoutubeResponse.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = response.value is ResponseState.Loading)
    val miniPlayerSwipeableState: SwipeableState<Int> = rememberSwipeableState(initialValue = 0)
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()

    // For YoutubeTopBar
    val toolbarHeight = 48.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    var toolbarOffsetHeightPx by remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = toolbarOffsetHeightPx + delta
                toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    if (isCardExpanded) {
        systemUiController.setSystemBarsColor(color = MaterialTheme.colorScheme.surface, darkIcons = !isSystemInDarkTheme())
    } else {
        systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = !isSystemInDarkTheme())
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentYoutubeChannelBasic != null
            ) {
                currentYoutubeChannelBasic?.streamLink?.let { nnCurrentVideoStreamLink ->
                    YoutubeMiniPlayer(
                        swipeableState = miniPlayerSwipeableState,
                        videoStreamLink = nnCurrentVideoStreamLink
                    )
                }
            }
        }
    ) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = isRefreshing,
            onRefresh = {
                with(youtubeViewModel) {
                    clearYoutubeList()
                    getCurrentYoutubeResponse()
                }
            },
            indicatorPadding = PaddingValues(bottom = toolbarHeight * 1.5f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                YoutubeVideoList(
                    expandMiniPlayer = {
                        coroutineScope.launch {
                            miniPlayerSwipeableState.animateTo(1, tween(durationMillis = YoutubeMiniPlayer.SWIPE_ANIMATION_DURATION))
                        }
                    },
                    nestedScrollConnection = nestedScrollConnection,
                    toolbarHeight = toolbarHeight
                )
                when (val result = response.value) {
                    is ResponseState.Empty -> {
                        Text("")
                    }
                    is ResponseState.Loading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    is ResponseState.Success -> {}
                    is ResponseState.Failure -> {
                        Text(result.errorMsg)
                    }
                }
            }
        }
        YoutubeTopBar(
            topBarHeight = toolbarHeight,
            topBarYOffset = toolbarOffsetHeightPx.roundToInt()
        )
    }
}
