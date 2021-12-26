package com.mutualmobile.iswipe.android.view.screens.youtube_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.states.ResponseState
import org.koin.androidx.compose.get

@Composable
fun YoutubeScreen(
    youtubeViewModel: YoutubeViewModel = get()
) {
    val response = youtubeViewModel.currentYoutubeResponse.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (val result = response.value) {
                is ResponseState.Empty -> {
                    ""
                }
                is ResponseState.Loading -> {
                    "Loading your videos..."
                }
                is ResponseState.Success -> {
                    result.data.toString()
                }
                is ResponseState.Failure -> {
                    result.errorMsg
                }
            }
        )
    }
}
