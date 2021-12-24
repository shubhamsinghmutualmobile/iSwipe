package com.mutualmobile.iswipe.android.view.screens.landing_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mutualmobile.iswipe.android.R
import com.mutualmobile.iswipe.android.view.screens.landing_screen.components.BoxIndicator
import com.mutualmobile.iswipe.android.view.screens.weather_screen.WeatherScreen
import com.mutualmobile.iswipe.android.view.screens.youtube_screen.YoutubeScreen
import kotlinx.coroutines.launch

enum class TabScreens(@DrawableRes val icon: Int) {
    Weather(icon = R.drawable.ic_weather),
    Youtube(icon = R.drawable.ic_youtube),
}

@Composable
fun GetTabScreen(index: TabScreens) {
    return when (index) {
        TabScreens.Weather -> WeatherScreen()
        TabScreens.Youtube -> YoutubeScreen()
    }
}

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun LandingScreen() {
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = androidx.compose.material.MaterialTheme.colors.isLight
    systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = !useDarkIcons)

    Scaffold(
        bottomBar = {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    BoxIndicator(tabIndex, tabPositions, pagerState)
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
            ) {
                TabScreens.values().forEachIndexed { index, screen ->
                    Tab(
                        modifier = Modifier.navigationBarsPadding(),
                        selected = tabIndex == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = screen.name, color = MaterialTheme.colorScheme.inverseOnSurface) },
                        icon = {
                            Image(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            count = TabScreens.values().size
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GetTabScreen(index = TabScreens.values()[index])
            }
        }
    }
}
