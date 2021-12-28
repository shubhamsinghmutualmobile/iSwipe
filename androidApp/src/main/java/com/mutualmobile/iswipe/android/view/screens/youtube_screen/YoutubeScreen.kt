package com.mutualmobile.iswipe.android.view.screens.youtube_screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.components.YoutubeMiniPlayer
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.components.YoutubeVideoList
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

object YoutubeScreen {
    const val DRAGGABLE_CARD_MIN_HEIGHT = 64
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun YoutubeScreen(
    youtubeViewModel: YoutubeViewModel = get()
) {
    val response = youtubeViewModel.currentYoutubeResponse.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = response.value is ResponseState.Loading)
    lateinit var swipeableState: SwipeableState<Int>
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            YoutubeMiniPlayer(expandMiniPlayer = { receivedSwipeableState ->
                swipeableState = receivedSwipeableState
            })
        }
    ) { scaffoldBodyPadding ->
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldBodyPadding)
                .statusBarsPadding(),
            state = isRefreshing,
            onRefresh = {
                with(youtubeViewModel) {
//                clearYoutubeList()
                    getCurrentYoutubeResponse()
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                YoutubeVideoList {
                    coroutineScope.launch {
                        swipeableState.animateTo(1, tween(durationMillis = YoutubeMiniPlayer.SWIPE_ANIMATION_DURATION))
                    }
                }
                when (val result = response.value) {
                    is ResponseState.Empty -> {
                        Text("")
                    }
                    is ResponseState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is ResponseState.Success -> {}
                    is ResponseState.Failure -> {
                        Text(result.errorMsg)
                    }
                }
            }
        }
    }
}
