package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.get

object ExoPlayer {
    const val PLAYER_BUTTON_SIZE = 32
    const val PLAYER_BUTTON_PADDING = 4
    const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
}

@Composable
fun ExoPlayer(
    youtubeViewModel: YoutubeViewModel = get(),
    videoUrl: String
) {
    val isVideoPlaying by youtubeViewModel.isVideoPlaying.collectAsState()

    val context = LocalContext.current
    val url = ExoPlayer.YOUTUBE_BASE_URL + videoUrl
    var extractedUrl = ""

    val exoPlayer = com.google.android.exoplayer2.ExoPlayer.Builder(context).build().apply {
        val dataSourceFactory: DefaultDataSource.Factory = DefaultDataSource.Factory(context)

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(extractedUrl)))

        setMediaSource(source)
        prepare()
    }

    var currentVideoProgress by remember { mutableStateOf(0.0f) }

    LaunchedEffect(Unit) {
        while (true) {
            currentVideoProgress = (exoPlayer.currentPosition.toFloat() / exoPlayer.contentDuration)
            delay(1000)
        }
    }

    if (isVideoPlaying) {
        exoPlayer.play()
    } else {
        exoPlayer.pause()
    }

    Column {
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            Row {
                Box(contentAlignment = Alignment.Center) {
                    AndroidView(
                        factory = { exoplayerContext ->
                            PlayerView(exoplayerContext).apply {
                                player = exoPlayer
                                useController = false
                            }
                        }
                    )
                    PlayerControlStrip(exoPlayer)
                }
                CollapsedPlayerTitleAndControls()
            }
            LinearProgressIndicator(
                progress = currentVideoProgress,
                modifier = Modifier.fillMaxWidth()
            )
        }
        ExpandedPlayerTitleRow()
    }
}
