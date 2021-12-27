package com.mutualmobile.iswipe.android.view.screens.youtube_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mutualmobile.iswipe.android.view.theme.YoutubePlayerTypography

@Composable
fun ExpandedPlayerTitleRow() {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = {}
            )
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Big title", style = YoutubePlayerTypography.titleLarge)
            Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null)
        }
        Text(
            "Big sub-title",
            style = YoutubePlayerTypography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(0.75f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
