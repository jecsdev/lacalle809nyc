package com.jecsdev.lacalle809nyc

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LaCalle809NYCApplication : Application() {
    override fun onCreate() {
        startKoin {
            androidContext(this@LaCalle809NYCApplication)
        }

        super.onCreate()
    }
}