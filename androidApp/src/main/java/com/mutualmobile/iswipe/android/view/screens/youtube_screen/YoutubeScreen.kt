package com.mutualmobile.iswipe.android.view.screens.youtube_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.components.ExoPlayer
import com.mutualmobile.iswipe.android.view.utils.isScrolledToEnd
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.states.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.get

object YoutubeScreen {
    const val DRAGGABLE_CARD_MIN_HEIGHT = 64
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun YoutubeScreen(
    youtubeViewModel: YoutubeViewModel = get()
) {
    val response = youtubeViewModel.currentYoutubeResponse.collectAsState()
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = response.value is ResponseState.Loading)
    val listOfVideos = youtubeViewModel.listOfYoutubeVideos.collectAsState()

    Scaffold(
        bottomBar = {
            val coroutineScope = rememberCoroutineScope()
            val swipeableState = rememberSwipeableState(initialValue = 0)
            val screenHeightDp = with(LocalDensity.current) {
                (
                    LocalConfiguration.current.screenHeightDp.toDp() - rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.systemBars
                    ).calculateTopPadding() + 3.dp
                    ).toPx()
            }
            val anchors = mapOf(0f to 0, screenHeightDp to 1)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = YoutubeScreen.DRAGGABLE_CARD_MIN_HEIGHT.dp)
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        reverseDirection = true
                    )
                    .height(swipeableState.offset.value.dp),
                shape = RoundedCornerShape(0),
                backgroundColor = Color.Transparent,
                onClick = {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            swipeableState.animateTo(1, tween(durationMillis = 500))
                        }
                    }
                },
                elevation = 0.dp
            ) {
                BackHandler(onBack = {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            swipeableState.animateTo(0, tween(durationMillis = 500))
                        }
                    }
                })

                ExoPlayer()
            }
        }
    ) { scaffoldBodyPadding ->
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldBodyPadding)
                .statusBarsPadding(),
            state = isRefreshing,
            onRefresh = {
                with(youtubeViewModel) {
//                clearYoutubeList()
                    getCurrentYoutubeResponse()
                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
                when (val result = response.value) {
                    is ResponseState.Empty -> {
                        Text("")
                    }
                    is ResponseState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is ResponseState.Success -> {}
                    is ResponseState.Failure -> {
                        Text(result.errorMsg)
                    }
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
