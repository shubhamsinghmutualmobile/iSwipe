package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.YoutubeScreen
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography

@Composable
fun ExoPlayer() {
    val context = LocalContext.current
    val url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"

    val exoPlayer = remember(context) {
        com.google.android.exoplayer2.ExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DefaultDataSource.Factory = DefaultDataSource.Factory(context)

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)))

            setMediaSource(source)
            prepare()
        }
    }
    Column {
        Row {
            AndroidView(
                factory = { exoplayerContext ->
                    PlayerView(exoplayerContext).apply {
                        player = exoPlayer
                    }
                }
            )
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
                        onClick = {},
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    }
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(),
                    onClick = {}
                )
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Big title", style = YoutubePlayerTypography.titleLarge)
                Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null)
            }
            Text(
                "Big sub-title",
                style = YoutubePlayerTypography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(0.75f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun YoutubeMiniPlayerText(
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
