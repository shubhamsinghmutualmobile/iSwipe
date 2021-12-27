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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.YoutubeScreen
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import org.koin.androidx.compose.get

@Composable
fun CollapsedPlayerTitleAndControls(youtubeViewModel: YoutubeViewModel = get()) {
    val isVideoPlaying by youtubeViewModel.isVideoPlaying.collectAsState()
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
            YoutubeMiniPlayerText(text = "Title Title Title Title Title", style = YoutubePlayerTypography.titleMedium)
            YoutubeMiniPlayerText(text = "Sub-Title Sub-Title Sub-Title", style = YoutubePlayerTypography.bodySmall)
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
                onClick = {},
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    }
}
