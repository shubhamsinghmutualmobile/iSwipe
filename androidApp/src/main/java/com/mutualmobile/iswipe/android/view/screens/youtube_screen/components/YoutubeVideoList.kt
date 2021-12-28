package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.utils.isScrolledToEnd
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun YoutubeVideoList(youtubeViewModel: YoutubeViewModel = get(), expandMiniPlayer: () -> Unit) {
    val listOfVideos = youtubeViewModel.listOfYoutubeVideos.collectAsState()
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        content = {
            listOfVideos.value.distinctBy { it.videoLinkEndPart }.forEach { video ->
                item { YoutubeVideoCard(video = video, expandMiniPlayer = expandMiniPlayer) }
            }
            if (listState.isScrolledToEnd()) {
                item { LoadingIndicator(listState) }
                youtubeViewModel.addNewItemsToList()
            }
        }
    )
}

@Composable
private fun LoadingIndicator(listState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        LaunchedEffect(key1 = "ScrollToShowCircularProgressIndicator") {
            coroutineScope.launch {
                listState.animateScrollToItem(listState.layoutInfo.totalItemsCount + 1)
            }
        }
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp),
            strokeWidth = 2.dp
        )
    }
}

@Composable
private fun YoutubeVideoCard(video: Item, youtubeViewModel: YoutubeViewModel = get(), expandMiniPlayer: () -> Unit) {
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    val iconInteractionSource by remember { mutableStateOf(MutableInteractionSource()) }
    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple()
        ) {
            youtubeViewModel.updateCurrentSelectedVideoItem(videoItem = video)
            expandMiniPlayer()
        }
    ) {
        Divider(
            thickness = 6.dp,
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
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
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .clickable(
                        interactionSource = iconInteractionSource,
                        indication = rememberRipple(bounded = false)
                    ) {}
            )
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
private fun YoutubeCaptionDotDivider() {
    Icon(
        painterResource(id = R.drawable.ic_dot),
        contentDescription = null,
        modifier = Modifier.size(2.dp),
        tint = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun YoutubeCardCaptionText(text: String?) {
    Text(text = text.orEmpty(), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 4.dp))
}