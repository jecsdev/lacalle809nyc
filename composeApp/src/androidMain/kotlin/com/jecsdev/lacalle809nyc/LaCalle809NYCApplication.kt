package com.jecsdev.lacalle809nyc

import android.app.Application
import com.jecsdev.lacalle809nyc.di.appModule
import com.jecsdev.lacalle809nyc.di.expoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LaCalle809NYCApplication : Application() {
    override fun onCreate() {
        startKoin {
            androidContext(this@LaCalle809NYCApplication)
            modules(
                appModule,
                expoModule
            )
        }

        super.onCreate()
    }
}