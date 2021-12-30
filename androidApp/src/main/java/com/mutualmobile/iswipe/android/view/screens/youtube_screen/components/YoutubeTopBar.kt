package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.mutualmobile.iswipe.android.R

@Composable
fun YoutubeTopBar(topBarHeight: Dp, topBarYOffset: Int) {
    val insets = LocalWindowInsets.current.statusBars.top
    val topInsetPx = with(LocalDensity.current) { insets.toDp() }

    Log.d("MyTag", "Value is: $insets")

    Card(
        elevation = 16.dp,
        shape = RoundedCornerShape(0),
        modifier = Modifier
            .height(topBarHeight + topInsetPx)
            .offset { IntOffset(x = 0, y = topBarYOffset * 2) },
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            BoxWithConstraints {
                Image(
                    if (isSystemInDarkTheme()) {
                        painterResource(id = R.drawable.youtube_logo_night)
                    } else {
                        painterResource(id = R.drawable.youtube_logo_day)
                    },
                    null,
                    modifier = Modifier
                        .padding(top = topInsetPx)
                        .fillMaxWidth(0.3f),
                    contentScale = ContentScale.Crop
                )
            }
            Row {
                IconButton(onClick = {}) {
                    Icon(painterResource(id = R.drawable.ic_cast), null)
                }
                IconButton(onClick = {}) {
                    Icon(painterResource(id = R.drawable.ic_bell), null)
                }
                IconButton(onClick = {}) {
                    Icon(painterResource(id = R.drawable.ic_search), null)
                }
                IconButton(onClick = {}) {
                    Icon(painterResource(id = R.drawable.ic_demo_user), null)
                }
            }
        }
    }
}
