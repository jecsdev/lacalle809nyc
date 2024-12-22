package com.jecsdev.lacalle809nyc.di

import com.jecsdev.lacalle809nyc.BuildConfig
import org.koin.dsl.module

val appModule = module {
    single {
        BuildConfig.BASE_URL
    }
}