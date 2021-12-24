package com.mutualmobile.iswipe.android.viewmodels

import app.cash.turbine.test
import com.mutualmobile.iswipe.android.utils.TestNetworkUtils
import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.apis.WeatherAPI
import com.mutualmobile.iswipe.data.network.apis.WeatherAPIImpl
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState
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

class WeatherViewModelTest : KoinTest {

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
                                        content = TestNetworkUtils.WEATHER_RESPONSE_SUCCESS.trimIndent(),
                                        status = HttpStatusCode.OK,
                                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                                    )
                                }
                            )
                        )
                    }
                    single<WeatherAPI> { WeatherAPIImpl(networkModule = get()) }
                    single { WeatherViewModel(weatherAPI = get()) }
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
    fun getCurrentWeatherEmitsSuccessStateToFlow() {
        val weatherViewModel: WeatherViewModel by inject()
        runBlocking {
            launch {
                weatherViewModel.currentWeather.test {
                    awaitItem() // Because getCurrentWeather() will first emit Loading state
                    val result = awaitItem()
                    assert(result is CurrentWeatherState.Success)
                    assert((result as CurrentWeatherState.Success).data.name == "Delhi")
                }
            }
        }
    }
}
