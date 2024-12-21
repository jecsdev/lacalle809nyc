package com.jecsdev.lacalle809nyc.di

import com.jecsdev.lacalle809nyc.data.repository.MediaPlayerRepositoryImpl
import org.koin.dsl.module

val expoModule = module {
    single {
        MediaPlayerRepositoryImpl()
    }
}