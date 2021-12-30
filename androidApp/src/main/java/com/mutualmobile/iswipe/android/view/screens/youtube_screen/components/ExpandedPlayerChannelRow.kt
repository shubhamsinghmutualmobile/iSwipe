package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import org.koin.androidx.compose.get

private object ExpandedPlayerChannelRow {
    const val IMAGE_SIZE = 32
    const val IMAGE_PADDING = 10
    const val SUBSCRIBE_BUTTON_PADDING = 8
    const val DIVIDER_ALPHA = 0.12f
}

@Composable
fun ExpandedPlayerChannelRow(
    youtubeViewModel: YoutubeViewModel = get()
) {

    val currentYoutubeChannelBasic by youtubeViewModel.currentYoutubeChannelBasic.collectAsState()

    Column {
        Spacer(modifier = Modifier.height(ExpandedPlayerChannelRow.IMAGE_PADDING.dp))
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ExpandedPlayerChannelRow.DIVIDER_ALPHA)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) {}
                .fillMaxWidth()
                .padding(end = ExpandedPlayerChannelRow.SUBSCRIBE_BUTTON_PADDING.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = rememberImagePainter(data = currentYoutubeChannelBasic?.channelAvatarUrl, builder = {
                        transformations(CircleCropTransformation())
                    }),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(ExpandedPlayerChannelRow.IMAGE_PADDING.dp)
                        .size(ExpandedPlayerChannelRow.IMAGE_SIZE.dp)
                )
                Column {
                    Text(currentYoutubeChannelBasic?.channelName.orEmpty())
                    Text("${currentYoutubeChannelBasic?.subscriberCount} subscribers", style = YoutubePlayerTypography.bodySmall)
                }
            }
            TextButton(onClick = {}) {
                Text(
                    "SUBSCRIBE",
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ExpandedPlayerChannelRow.DIVIDER_ALPHA)
        )
    }
}
