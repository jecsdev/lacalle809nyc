package io.jecsdev.lacalle809nyc.kmm.presentation.ui

import io.jecsdev.lacalle809nyc.kmm.presentation.ui.di.PresentationUiDependencyInjector
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            PresentationUiDependencyInjector.modules
        )
    }
}
