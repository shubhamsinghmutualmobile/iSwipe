package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import org.koin.androidx.compose.get

@Composable
fun ExpandedPlayerActionRow(
    youtubeViewModel: YoutubeViewModel = get()
) {
    val currentSelectedVideoItem by youtubeViewModel.currentSelectedVideoItem.collectAsState()
    val listOfItems = listOf(
        R.drawable.ic_like to currentSelectedVideoItem?.statistics?.likeCount.orEmpty(),
        R.drawable.ic_dislike to "Dislike",
        R.drawable.ic_share to "Share",
        R.drawable.ic_create to "Create",
        R.drawable.ic_download to "Download",
        R.drawable.ic_save to "Save"
    )
    LazyRow {
        item {
            Spacer(modifier = Modifier.padding(start = 16.dp))
        }
        items(listOfItems.size) { index ->
            IconButtonWithLabel(icon = listOfItems[index].first, label = listOfItems[index].second)
        }
    }
}

@Composable
fun IconButtonWithLabel(
    @DrawableRes icon: Int,
    label: String,
    contentDescription: String? = null
) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(painterResource(id = icon), contentDescription)
        }
        Text(label, style = YoutubePlayerTypography.bodySmall)
    }
}
