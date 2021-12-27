package com.mutualmobile.iswipe.android.view.screens.landing_screen.components

import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

@ExperimentalPagerApi
@Composable
fun BoxIndicator(tabIndex: Int, tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(targetState = tabIndex, label = "")
    val leftIndicator = tabPositions[transition.currentState].left
    val rightIndicator = tabPositions[transition.currentState].right
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions)
            .width(rightIndicator - leftIndicator)
            .padding(4.dp)
            .navigationBarsPadding()
            .fillMaxSize()
            .border(
                border = BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(20)
            )
    )
}
