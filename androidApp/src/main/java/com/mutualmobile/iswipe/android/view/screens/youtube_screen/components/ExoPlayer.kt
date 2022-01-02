package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExoPlayer(
    youtubeViewModel: YoutubeViewModel = get(),
    videoUrl: String
) {
    val isVideoPlaying by youtubeViewModel.isVideoPlaying.collectAsState()
    val isCardExpanded by youtubeViewModel.isCardExpanded.collectAsState()

    val context = LocalContext.current

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    val exoPlayer by remember {
        mutableStateOf(
            com.google.android.exoplayer2.ExoPlayer.Builder(context).build().apply {
                val dataSourceFactory: DefaultDataSource.Factory = DefaultDataSource.Factory(context)

                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))

                setMediaSource(source)
                prepare()
            }
        )
    }

    var currentVideoProgress by remember { mutableStateOf(0.0f) }

    LaunchedEffect(Unit) {
        while (true) {
            if (!isCardExpanded) {
                coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.collapse() }
            }
            currentVideoProgress = (exoPlayer.currentPosition.toFloat() / exoPlayer.contentDuration)
            delay(1000)
        }
    }

    if (isVideoPlaying) {
        exoPlayer.play()
    } else {
        exoPlayer.pause()
    }
    BoxWithConstraints {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                YoutubeDescriptionSheet(
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    maxHeight = this@BoxWithConstraints.maxHeight
                )
            },
            backgroundColor = Color.Transparent,
            sheetBackgroundColor = if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) Color.Transparent else MaterialTheme.colorScheme.background,
        ) {
            Column {
                Box(
                    contentAlignment = Alignment.BottomStart
                ) {
                    Row {
                        BoxWithConstraints(contentAlignment = Alignment.Center) {
                            AndroidView(
                                modifier = Modifier
                                    .aspectRatio(1.79f),
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
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                ExpandedPlayerTitleRow(bottomSheetScaffoldState = bottomSheetScaffoldState)
                ExpandedPlayerActionRow()
                ExpandedPlayerChannelRow()
            }
        }
    }
}
