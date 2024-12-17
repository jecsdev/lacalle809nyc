package com.jecsdev.lacalle809nyc.kmm

import android.app.Application
import com.jecsdev.lacalle809nyc.shared.presentation.ui.di.PresentationUiDependencyInjector
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LaCalle809Application : Application() {
    override fun onCreate() {
        startKoin {
            androidContext(this@LaCalle809Application)
            modules(PresentationUiDependencyInjector.modules)
        }

        super.onCreate()
    }
}