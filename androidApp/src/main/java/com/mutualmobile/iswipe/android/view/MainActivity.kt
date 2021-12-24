package com.mutualmobile.iswipe.android.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mutualmobile.iswipe.android.view.screens.landing_screen.LandingScreen
import com.mutualmobile.iswipe.android.view.theme.ISwipeTheme

private const val TAG = "MainActivityTAG"

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = androidx.compose.material.MaterialTheme.colors.isLight
            systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = useDarkIcons)

            ProvideWindowInsets {
                ISwipeTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LandingScreen()
                        }
                    }
                }
            }
        }
    }
}
