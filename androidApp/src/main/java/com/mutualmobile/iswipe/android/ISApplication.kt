package com.mutualmobile.iswipe.android

import android.app.Application
import com.mutualmobile.iswipe.data.di.network
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ISApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ISApplication)
            modules(network)
        }
    }
}
