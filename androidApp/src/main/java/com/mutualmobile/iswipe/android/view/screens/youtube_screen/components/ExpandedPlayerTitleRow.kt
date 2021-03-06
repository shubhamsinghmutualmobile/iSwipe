package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography
import com.mutualmobile.iswipe.viewmodels.YoutubeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

private object ExpandedPlayerTitleRow {
    const val TEXT_PADDING_TOP = 4
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandedPlayerTitleRow(youtubeViewModel: YoutubeViewModel = get(), bottomSheetScaffoldState: BottomSheetScaffoldState) {
    val currentVideoItem by youtubeViewModel.currentSelectedVideoItem.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            )
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                currentVideoItem?.snippet?.title.orEmpty(),
                style = YoutubePlayerTypography.titleLarge,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExpandedPlayerTitleRowText(
                texts = arrayOf(
                    Pair("${currentVideoItem?.statistics?.viewCount} views", false),
                    Pair("${currentVideoItem?.snippet?.publishedAt} ago", false),
                    Pair(
                        currentVideoItem?.snippet?.tags?.subList(0, 3)?.joinToString(transform = { existingText ->
                            "#$existingText"
                        }, separator = " ").orEmpty(),
                        true
                    )
                )
            )
        }
    }
}

@Composable
private fun ExpandedPlayerTitleRowText(
    vararg texts: Pair<String, Boolean>
) {
    val builder = buildAnnotatedString {
        texts.forEachIndexed { index, textPair ->
            val textColor = if (textPair.second) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface.copy(0.75f)

            val textValue = if (index + 1 < texts.size - 1 && !texts[index+1].second) "${textPair.first} . "
            else "${textPair.first} "

            append(AnnotatedString(textValue, spanStyle = SpanStyle(color = textColor, fontSize = 12.sp)))
        }
    }

    Text(
        builder,
        modifier = Modifier.padding(top = ExpandedPlayerTitleRow.TEXT_PADDING_TOP.dp)
    )
}
