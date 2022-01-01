package com.mutualmobile.iswipe.android.view.screens.weather_screen.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.layout90Rotated() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(-placeable.height, (placeable.width - placeable.height) / 2)
        }
    }
