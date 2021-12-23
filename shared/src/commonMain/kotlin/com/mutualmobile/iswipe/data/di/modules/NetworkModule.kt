package com.mutualmobile.iswipe.data.di.modules

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json

class NetworkModule {
    fun getNetworkClient(): HttpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { }
            serializer = KotlinxSerializer(json = json)
        }
    }
}
