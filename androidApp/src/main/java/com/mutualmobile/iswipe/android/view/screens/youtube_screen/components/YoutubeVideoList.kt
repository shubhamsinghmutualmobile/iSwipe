package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.utils.isScrolledToEnd
import com.mutualmobile.iswipe.data.states.ResponseState
import com.mutualmobile.iswipe.viewmodels.YoutubeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun YoutubeVideoList(
    youtubeViewModel: YoutubeViewModel = get(),
    expandMiniPlayer: () -> Unit,
    nestedScrollConnection: NestedScrollConnection,
    toolbarHeight: Dp
) {
    val listOfVideos by youtubeViewModel.listOfYoutubeVideos.collectAsState()
    val listState = rememberLazyListState()
    val response by youtubeViewModel.currentYoutubeResponse.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .animateContentSize(),
        content = {
            item { Spacer(modifier = Modifier.height(toolbarHeight)) }
            listOfVideos.distinctBy { it.videoLinkEndPart }.forEach { video ->
                item { YoutubeVideoCard(video = video, expandMiniPlayer = expandMiniPlayer) }
            }
            item {
                AnimatedVisibility(
                    visible = listState.isScrolledToEnd() && response !is ResponseState.Loading && listState.layoutInfo.totalItemsCount > 2,
                    enter = slideInVertically(initialOffsetY = { it / 2 }),
                    exit = slideOutVertically(targetOffsetY = { it / 2 })
                ) {
                    LoadingIndicator(listState)
                }
            }
            if (listState.isScrolledToEnd()) {
                coroutineScope.launch {
                    listState.scroll {
                        this.scrollBy(200f)
                    }
                }
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
            .padding(bottom = 100.dp, top = 16.dp)
            .fillMaxWidth()
    ) {
        LaunchedEffect(Unit) {
            coroutineScope.launch {
                listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
            }
        }
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp),
            strokeWidth = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun YoutubeCaptionDotDivider(paddingTop: Dp = 0.dp) {
    Icon(
        painterResource(id = R.drawable.ic_dot),
        contentDescription = null,
        modifier = Modifier
            .padding(top = paddingTop)
            .size(2.dp),
        tint = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun YoutubeCardCaptionText(text: String?) {
    Text(text = text.orEmpty(), style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 4.dp))
}
