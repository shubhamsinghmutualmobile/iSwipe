package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.YoutubeScreen
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun CollapsedPlayerTitleAndControls(youtubeViewModel: YoutubeViewModel = get()) {
    val isVideoPlaying by youtubeViewModel.isVideoPlaying.collectAsState()
    val currentVideoItem by youtubeViewModel.currentSelectedVideoItem.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(0.6f)
                .height(YoutubeScreen.DRAGGABLE_CARD_MIN_HEIGHT.dp),
            verticalArrangement = Arrangement.Center
        ) {
            YoutubeMiniPlayerText(text = currentVideoItem?.snippet?.title.orEmpty(), style = YoutubePlayerTypography.titleMedium)
            YoutubeMiniPlayerText(text = currentVideoItem?.snippet?.channelTitle.orEmpty(), style = YoutubePlayerTypography.bodySmall)
        }
        Row {
            IconButton(
                onClick = { youtubeViewModel.toggleIsVideoPlaying(isVideoPlaying) },
            ) {
                if (isVideoPlaying) {
                    Icon(painter = painterResource(id = R.drawable.ic_pause), contentDescription = null)
                } else {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                }
            }
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        youtubeViewModel.toggleIsVideoPlaying(isVideoPlaying = true)
                        delay(100)
                        if (!isVideoPlaying) {
                            youtubeViewModel.updateCurrentSelectedVideoItem(videoItem = null)
                        }
                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}

@Composable
fun YoutubeMiniPlayerText(
    text: String,
    style: TextStyle
) {
    Text(
        text = text,
        style = style,
        softWrap = false,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
