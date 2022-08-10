package com.chlqudco.develop.byeongchaetalk

import android.app.Application
import com.chlqudco.develop.byeongchaetalk.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AppApplication)
            modules(appModule)
        }
    }
}