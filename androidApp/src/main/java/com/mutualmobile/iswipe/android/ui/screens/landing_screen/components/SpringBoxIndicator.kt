package com.mutualmobile.iswipe.android.ui.screens.landing_screen.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.TabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun SpringBoxIndicator(tabIndex: Int, tabPositions: List<TabPosition>) {
    val transition = updateTransition(targetState = tabIndex, label = "")
    val leftIndicator by transition.animateDp(label = "", transitionSpec = {
        spring(stiffness = Spring.StiffnessLow)
    }) {
        tabPositions[it].left
    }
    val rightIndicator by transition.animateDp(label = "", transitionSpec = {
        spring(stiffness = Spring.StiffnessMedium)
    }) {
        tabPositions[it].right
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = leftIndicator)
            .width(rightIndicator - leftIndicator)
            .padding(4.dp)
            .navigationBarsPadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
    )
}
