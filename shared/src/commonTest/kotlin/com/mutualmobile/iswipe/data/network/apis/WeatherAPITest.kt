package com.mutualmobile.iswipe.data.network.apis

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.utils.TestNetworkUtils
import com.mutualmobile.iswipe.data.states.weather.CurrentWeatherState
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

class WeatherAPITest : KoinTest {
    private val testSuccessWeatherModule = module {
        single {
            NetworkModule(
                HttpClient(
                    MockEngine {
                        respond(
                            content = ByteReadChannel(
                                TestNetworkUtils.WEATHER_RESPONSE_SUCCESS.trimIndent()
                            ),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }
                )
            )
        }
        single<WeatherAPI> { WeatherAPIImpl(get()) }
    }

    @BeforeTest
    fun setupTest() {
        startKoin {
            modules(
                testSuccessWeatherModule
            )
        }
    }

    @AfterTest
    fun teardown() {
        stopKoin()
    }

    @Test
    fun getCurrentWeather_returns_CurrentWeatherState_Success() {
        val weatherApi: WeatherAPI by inject()
        runBlocking {
            assertTrue {
                weatherApi.getCurrentWeather() is CurrentWeatherState.Success
            }
            assertEquals("Delhi", (weatherApi.getCurrentWeather() as CurrentWeatherState.Success).data.name)
        }
    }
}
