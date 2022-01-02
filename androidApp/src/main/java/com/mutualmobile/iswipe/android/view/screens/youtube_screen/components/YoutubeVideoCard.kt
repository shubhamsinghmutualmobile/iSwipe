package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun YoutubeVideoCard(video: Item, youtubeViewModel: YoutubeViewModel = get(), expandMiniPlayer: () -> Unit) {
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    val iconInteractionSource by remember { mutableStateOf(MutableInteractionSource()) }
    val coroutineScope = rememberCoroutineScope()
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    val currentYoutubeVideoItem by youtubeViewModel.currentSelectedVideoItem.collectAsState()
    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple()
        ) {
            if (currentYoutubeVideoItem == video) {
                expandMiniPlayer()
            } else {
                coroutineScope.launch {
                    with(youtubeViewModel) {
                        setMiniPlayerLoading(isLoading = true)
                        toggleIsVideoPlaying(isVideoPlaying = true)
                        delay(100)
                        updateCurrentSelectedVideoItem(videoItem = null)
                        delay(100)
                        updateCurrentSelectedVideoItem(videoItem = video)
                        expandMiniPlayer()
                        toggleIsVideoPlaying(isVideoPlaying = false)
                    }
                }
            }
        }
    ) {
        Divider(
            thickness = 6.dp,
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        )
        Image(
            painter = rememberImagePainter(
                data = video.snippet?.thumbnails?.high?.url,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1.3f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 8.dp)
        ) {
            Text(
                text = video.snippet?.title.orEmpty(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(0.9f),
            )
            Box {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable(
                            interactionSource = iconInteractionSource,
                            indication = rememberRipple(bounded = false)
                        ) {
                            isDropDownMenuExpanded = true
                        }
                )
                YoutubeVideoCardMoreDialog(isExpanded = isDropDownMenuExpanded, closeMenu = { isDropDownMenuExpanded = false })
            }
        }
        Row(
            modifier = Modifier.padding(top = 4.dp, start = 12.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            YoutubeCardCaptionText(text = video.snippet?.channelTitle)
            YoutubeCaptionDotDivider()
            YoutubeCardCaptionText(text = "${video.statistics?.viewCount} views")
            YoutubeCaptionDotDivider()
            YoutubeCardCaptionText(text = "${video.snippet?.publishedAt} ago")
        }
    }
}

@Composable
fun YoutubeVideoCardMoreDialog(isExpanded: Boolean, closeMenu: () -> Unit) {
    val listOfMenuItems = listOf(
        "Save to Watch Later",
        "Save to playlist",
        "Download video",
        "Share",
        "Not interested",
        "Don't recommend channel",
        "Report"
    )
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { closeMenu() },
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        listOfMenuItems.forEach { menuItemText ->
            DropdownMenuItem(onClick = { closeMenu() }) {
                Text(menuItemText)
            }
        }
    }
}
