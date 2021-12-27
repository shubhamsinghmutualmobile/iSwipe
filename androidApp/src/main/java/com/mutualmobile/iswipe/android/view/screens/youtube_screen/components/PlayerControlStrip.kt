package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import org.koin.androidx.compose.get

@Composable
fun PlayerControlStrip(
    exoPlayer: com.google.android.exoplayer2.ExoPlayer,
    youtubeViewModel: YoutubeViewModel = get()
) {
    val isVideoPlaying by youtubeViewModel.isVideoPlaying.collectAsState()
    val isCardExpanded by youtubeViewModel.isCardExpanded.collectAsState()
    val isCardTouched by youtubeViewModel.isCardTouched.collectAsState()

    AnimatedVisibility(
        visible = isCardExpanded && isCardTouched,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { exoPlayer.seekBack() }) {
                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    null,
                    modifier = Modifier
                        .size(ExoPlayer.PLAYER_BUTTON_SIZE.dp)
                        .padding(end = ExoPlayer.PLAYER_BUTTON_PADDING.dp)
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
                        modifier = Modifier.size(ExoPlayer.PLAYER_BUTTON_SIZE.dp)
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
                        modifier = Modifier.size(ExoPlayer.PLAYER_BUTTON_SIZE.dp)
                    )
                }
            }
            IconButton(onClick = { exoPlayer.seekForward() }) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    null,
                    modifier = Modifier
                        .size(ExoPlayer.PLAYER_BUTTON_SIZE.dp)
                        .padding(start = ExoPlayer.PLAYER_BUTTON_PADDING.dp)
                )
            }
        }
    }
}
