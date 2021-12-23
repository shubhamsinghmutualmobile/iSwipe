package com.mutualmobile.iswipe.android.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mutualmobile.iswipe.android.view.screens.landing_screen.LandingScreen
import com.mutualmobile.iswipe.android.view.theme.ISwipeTheme
import com.mutualmobile.iswipe.data.network.apis.WeatherAPI
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

private const val TAG = "MainActivityTAG"

class MainActivity : AppCompatActivity() {
    private val weatherApi: WeatherAPI by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        MainScope().launch {
            Toast.makeText(applicationContext, weatherApi.getCurrentWeather().toString(), Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreate: ${weatherApi.getCurrentWeather()}")
        }
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
