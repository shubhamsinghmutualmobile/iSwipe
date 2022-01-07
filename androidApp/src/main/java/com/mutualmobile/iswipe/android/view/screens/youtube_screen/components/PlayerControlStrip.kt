package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.viewmodels.YoutubeViewModel
import org.koin.androidx.compose.get

private object PlayerControlStrip {
    const val PLAYER_BUTTON_SIZE = 48
    const val PLAYER_BUTTON_PADDING = 32
}

@Composable
fun PlayerControlStrip(
    exoPlayer: ExoPlayer,
    youtubeViewModel: YoutubeViewModel = get(),
    onBack: () -> Unit
) {
    val isVideoPlaying by youtubeViewModel.isVideoPlaying.collectAsState()
    val isCardExpanded by youtubeViewModel.isCardExpanded.collectAsState()
    val isCardTouched by youtubeViewModel.isCardTouched.collectAsState()

    AnimatedVisibility(
        visible = isCardExpanded && isCardTouched,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.79f)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.MoreVert, null)
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_fullscreen), null)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { exoPlayer.seekBack() },
                    modifier = Modifier.padding(end = PlayerControlStrip.PLAYER_BUTTON_PADDING.dp)
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        null,
                        modifier = Modifier
                            .size(PlayerControlStrip.PLAYER_BUTTON_SIZE.dp)
                    )
                }
                if (isVideoPlaying) {
                    IconButton(
                        onClick = {
                            youtubeViewModel.toggleIsVideoPlaying(isVideoPlaying = true)
                            exoPlayer.pause()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pause),
                            contentDescription = null,
                            modifier = Modifier.size(PlayerControlStrip.PLAYER_BUTTON_SIZE.dp)
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            youtubeViewModel.toggleIsVideoPlaying(isVideoPlaying = false)
                            exoPlayer.play()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(PlayerControlStrip.PLAYER_BUTTON_SIZE.dp)
                        )
                    }
                }
                IconButton(
                    onClick = { exoPlayer.seekForward() },
                    modifier = Modifier.padding(start = PlayerControlStrip.PLAYER_BUTTON_PADDING.dp)
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        null,
                        modifier = Modifier
                            .size(PlayerControlStrip.PLAYER_BUTTON_SIZE.dp)
                    )
                }
            }
        }
    }
}
