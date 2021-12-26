package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.models.youtube_trending_videos.YoutubeTrendingVideosResponse
import com.mutualmobile.iswipe.data.network.utils.TestNetworkUtils
import com.mutualmobile.iswipe.data.states.ResponseState
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class YoutubeAPITest : KoinTest {
    private val testSuccessYoutubeModule = module {
        single {
            NetworkModule(
                HttpClient(
                    MockEngine {
                        respond(
                            content = ByteReadChannel(
                                TestNetworkUtils.YOUTUBE_VIDEOS_RESPONSE_SUCCESS.trimIndent()
                            ),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }
                )
            )
        }
        single<YoutubeAPI> { YoutubeAPIImpl(get()) }
    }

    @BeforeTest
    fun setupTest() {
        startKoin {
            modules(
                testSuccessYoutubeModule
            )
        }
    }

    @AfterTest
    fun teardown() {
        stopKoin()
    }

    @Test
    fun getCurrentWeather_returns_CurrentWeatherState_Success() {
        val youtubeApi: YoutubeAPI by inject()
        runBlocking {
            assertTrue {
                youtubeApi.getTrendingVideos() is ResponseState.Success<YoutubeTrendingVideosResponse>
            }
            assertEquals(
                "Wk9jiFfuS_n0gU6tPEvmyWtCrBQ",
                (youtubeApi.getTrendingVideos() as ResponseState.Success<YoutubeTrendingVideosResponse>).data.etag
            )
        }
    }
}
