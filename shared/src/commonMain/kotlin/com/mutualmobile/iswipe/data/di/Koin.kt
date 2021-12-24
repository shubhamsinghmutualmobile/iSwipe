package com.mutualmobile.iswipe.data.di

import com.mutualmobile.iswipe.data.di.modules.NetworkModule
import com.mutualmobile.iswipe.data.network.apis.WeatherAPI
import com.mutualmobile.iswipe.data.network.apis.WeatherAPIImpl
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(network)
}

fun initKoin() = initKoin {}

val network = module {
    single { NetworkModule() }
    single<WeatherAPI> { WeatherAPIImpl(get()) }
}
