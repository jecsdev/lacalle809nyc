package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.di

import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences.dataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun getPlatformInjects(): List<Module> = listOf(
    module {
        singleOf(::dataStore)
    }
)
