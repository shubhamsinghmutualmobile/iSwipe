package com.mutualmobile.iswipe.viewmodels

import app.cash.turbine.test
import com.mutualmobile.iswipe.android.utils.TestNetworkUtils
import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPIImpl
import com.mutualmobile.iswipe.data.states.ResponseState
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class YoutubeViewModelTest : KoinTest {

    // Because we don't have direct access to runBlocking in Unit Tests (ref. https://stackoverflow.com/questions/58303961)
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        startKoin {
            modules(
                module {
                    single {
                        NetworkModule(
                            HttpClient(
                                MockEngine {
                                    respond(
                                        content = TestNetworkUtils.YOUTUBE_VIDEOS_RESPONSE_SUCCESS.trimIndent(),
                                        status = HttpStatusCode.OK,
                                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                                    )
                                }
                            )
                        )
                    }
                    single<YoutubeAPI> { YoutubeAPIImpl(networkModule = get()) }
                    single { YoutubeViewModel(youtubeApi = get()) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        mainThreadSurrogate.close()
    }

    @Test
    fun getCurrentYoutubeResponseEmitsSuccessStateToFlow() {
        val youtubeViewModel: YoutubeViewModel by inject()
        runBlocking {
            launch {
                youtubeViewModel.currentYoutubeResponse.test {
                    awaitItem() // Empty state
                    awaitItem() // Loading state
                    val result = awaitItem()
                    assert(result is ResponseState.Success)
                    assert((result as ResponseState.Success).data.etag == "Wk9jiFfuS_n0gU6tPEvmyWtCrBQ")
                }
            }
        }
    }
}
