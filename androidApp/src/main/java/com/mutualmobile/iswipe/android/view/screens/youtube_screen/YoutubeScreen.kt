package com.mutualmobile.iswipe.android.view.screens.youtube_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.utils.isScrolledToEnd
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun YoutubeScreen(
    youtubeViewModel: YoutubeViewModel = get()
) {
    val response = youtubeViewModel.currentYoutubeResponse.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = response.value is ResponseState.Loading)

    val listOfVideos = youtubeViewModel.listOfYoutubeVideos.collectAsState()

    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        state = isRefreshing,
        onRefresh = {
            with(youtubeViewModel) {
                clearYoutubeList()
                getCurrentYoutubeResponse()
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val result = response.value) {
                is ResponseState.Empty -> {
                    Text("")
                }
                is ResponseState.Loading -> {
                    Text("Loading your videos...")
                }
                is ResponseState.Success -> {
                    val listState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            listOfVideos.value.distinctBy { it.videoLinkEndPart }.forEach { video ->
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {}
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
                                                modifier = Modifier.size(18.dp)
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
                            }
                            if (listState.isScrolledToEnd()) {
                                item {
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
                                youtubeViewModel.addNewItemsToList()
                            }
                        }
                    )
                }
                is ResponseState.Failure -> {
                    Text(result.errorMsg)
                }
            }
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
