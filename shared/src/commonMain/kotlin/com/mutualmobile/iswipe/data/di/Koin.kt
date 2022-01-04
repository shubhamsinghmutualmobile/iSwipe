package com.mutualmobile.iswipe.data.di

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.apis.WeatherAPI
import com.mutualmobile.iswipe.data.network.apis.WeatherAPIImpl
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPI
import com.mutualmobile.iswipe.data.network.apis.YoutubeAPIImpl
import com.mutualmobile.iswipe.data.network.utils.TestNetworkUtils
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@ExperimentalSerializationApi
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(network)
}

@ExperimentalSerializationApi
fun initKoin() = initKoin {}

private fun getMockClient(response: String): HttpClient {
    return HttpClient(
        MockEngine {
            delay(3000)
            respond(
                content = ByteReadChannel(response.trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    )
}

@ExperimentalSerializationApi
val network = module {
    single { NetworkModule() }
    single<WeatherAPI> { WeatherAPIImpl(get()) }
    single<YoutubeAPI> { YoutubeAPIImpl(get()) }
}
