package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

private object YoutubeDescriptionSheet {
    const val DIVIDER_ALPHA = 0.12f
    const val DIVIDER_TOP_PADDING = 8
    const val CORNER_PERCENTAGE = 4
    const val SURFACE_ELEVATION = 4
    const val ROW_HORIZONTAL_PADDING = 8
    const val DESCRIPTION_TITLE_PADDING = 16
    const val IMAGE_SIZE = 24
    const val SHEET_SCROLL_ELEVATION = 16
}

@OptIn(ExperimentalMaterialApi::class)
fun CoroutineScope.collapseBottomSheetWithListReset(bottomSheetScaffoldState: BottomSheetScaffoldState, listState: LazyListState) {
    this.launch {
        bottomSheetScaffoldState.bottomSheetState.collapse()
        listState.scrollToItem(0)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YoutubeDescriptionSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    youtubeViewModel: YoutubeViewModel = get()
) {
    val currentVideoItem by youtubeViewModel.currentSelectedVideoItem.collectAsState()
    val currentChannel by youtubeViewModel.currentYoutubeChannelBasic.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    if (bottomSheetScaffoldState.bottomSheetState.isExpanded || bottomSheetScaffoldState.bottomSheetState.isAnimationRunning) {
        BackHandler {
            coroutineScope.launch {
                collapseBottomSheetWithListReset(
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                    listState = listState
                )
            }
        }
    }
    Surface(
        tonalElevation = YoutubeDescriptionSheet.SURFACE_ELEVATION.dp,
        shape = RoundedCornerShape(
            topStartPercent = YoutubeDescriptionSheet.CORNER_PERCENTAGE,
            topEndPercent = YoutubeDescriptionSheet.CORNER_PERCENTAGE
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.655f)
        ) {
            Surface(tonalElevation = if (listState.firstVisibleItemIndex > 0) YoutubeDescriptionSheet.SHEET_SCROLL_ELEVATION.dp else 0.dp) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = YoutubeDescriptionSheet.DIVIDER_TOP_PADDING.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Divider(
                            modifier = Modifier.fillMaxWidth(0.2f),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = YoutubeDescriptionSheet.DIVIDER_ALPHA),
                            thickness = 4.dp,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = YoutubeDescriptionSheet.ROW_HORIZONTAL_PADDING.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Description",
                            modifier = Modifier.padding(start = YoutubeDescriptionSheet.ROW_HORIZONTAL_PADDING.dp)
                        )
                        IconButton(onClick = {
                            coroutineScope.launch {
                                collapseBottomSheetWithListReset(
                                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                                    listState = listState
                                )
                            }
                        }) {
                            Icon(Icons.Default.Close, contentDescription = null)
                        }
                    }
                }
            }
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    Spacer(modifier = Modifier.height(YoutubeDescriptionSheet.DESCRIPTION_TITLE_PADDING.dp))
                }
                item {
                    Text(
                        currentVideoItem?.snippet?.title.orEmpty(),
                        style = YoutubePlayerTypography.titleLarge,
                        modifier = Modifier.padding(horizontal = YoutubeDescriptionSheet.DESCRIPTION_TITLE_PADDING.dp)
                    )
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = YoutubeDescriptionSheet.ROW_HORIZONTAL_PADDING.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = currentChannel?.channelAvatarUrl,
                                builder = {
                                    transformations(CircleCropTransformation())
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(YoutubeDescriptionSheet.ROW_HORIZONTAL_PADDING.dp)
                                .size(YoutubeDescriptionSheet.IMAGE_SIZE.dp)
                        )
                        Text(
                            currentChannel?.channelName.orEmpty(),
                            style = YoutubePlayerTypography.titleMedium
                        )
                    }
                }
                currentVideoItem?.let { nnCurrentVideoItem ->
                    item {
                        YoutubeDescriptionStatisticsRow(nnCurrentVideoItem)
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(0.92f)
                                .padding(top = YoutubeDescriptionSheet.DIVIDER_TOP_PADDING.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = YoutubeDescriptionSheet.DIVIDER_ALPHA)
                        )
                    }
                }
                item {
                    Text(
                        currentVideoItem?.snippet?.description.orEmpty(),
                        modifier = Modifier.padding(YoutubeDescriptionSheet.DESCRIPTION_TITLE_PADDING.dp),
                        style = YoutubePlayerTypography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun YoutubeDescriptionStatisticsRow(currentVideoItem: Item) {

    val likeCount = currentVideoItem.statistics?.likeCount.orEmpty()
    val viewCount = currentVideoItem.statistics?.viewCount.orEmpty()
    val yearUploaded = currentVideoItem.snippet?.publishedAt?.substring(0, 10).orEmpty()

    val listOfStats = listOf(
        Pair("Likes", likeCount),
        Pair("Views", viewCount),
        Pair("Published at", yearUploaded),
    )
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(YoutubeDescriptionSheet.ROW_HORIZONTAL_PADDING.dp)
    ) {
        listOfStats.forEach { pair ->
            YoutubeDescriptionStatistic(title = pair.first, value = pair.second)
        }
    }
}

@Composable
private fun YoutubeDescriptionStatistic(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = YoutubePlayerTypography.titleLarge)
        Text(text = title, style = YoutubePlayerTypography.bodySmall)
    }
}
