package com.mutualmobile.iswipe.data.di.modules

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

class NetworkModule constructor(
    private val clientEngineFactory: HttpClient = HttpClient(CIO)
) {
    fun getNetworkClient(): HttpClient = clientEngineFactory.config {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
}
