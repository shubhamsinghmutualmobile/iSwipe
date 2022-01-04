package com.mutualmobile.iswipe.android

import android.app.Application
import com.mutualmobile.iswipe.android.utils.DownloaderImpl
import com.mutualmobile.iswipe.data.di.network
import com.mutualmobile.iswipe.viewmodels.WeatherViewModel
import com.mutualmobile.iswipe.viewmodels.YoutubeViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.schabi.newpipe.extractor.NewPipe

class ISApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewPipe.init(DownloaderImpl(okHttpClientBuilder = OkHttpClient.Builder()))
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ISApplication)
            modules(
                network,
                module {
                    single { WeatherViewModel(get()) }
                    single { YoutubeViewModel(get()) }
                }
            )
        }
    }
}
