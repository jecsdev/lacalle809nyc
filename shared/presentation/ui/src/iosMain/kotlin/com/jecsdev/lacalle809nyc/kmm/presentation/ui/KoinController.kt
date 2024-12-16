package com.jecsdev.lacalle809nyc.kmm.presentation.ui

import com.jecsdev.lacalle809nyc.shared.presentation.ui.di.PresentationUiDependencyInjector
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            PresentationUiDependencyInjector.modules
        )
    }
}
