package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mutualmobile.iswipe.android.R

@Composable
fun YoutubeTopBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints {
            Image(
                if (isSystemInDarkTheme()) {
                    painterResource(id = R.drawable.youtube_logo_night)
                } else {
                    painterResource(id = R.drawable.youtube_logo_day)
                },
                null,
                modifier = Modifier.fillMaxWidth(0.27f)
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
