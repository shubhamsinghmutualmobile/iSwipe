package com.mutualmobile.iswipe.android

import android.app.Application
import com.mutualmobile.iswipe.android.view.utils.DownloaderImpl
import com.mutualmobile.iswipe.android.viewmodels.WeatherViewModel
import com.mutualmobile.iswipe.android.viewmodels.YoutubeViewModel
import com.mutualmobile.iswipe.data.di.network
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.schabi.newpipe.extractor.NewPipe

class ISApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewPipe.init(DownloaderImpl.init(null))
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
