package com.jecsdev.lacalle809nyc.di

import com.jecsdev.lacalle809nyc.data.repository.MediaPlayerRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val expoModule = module {
   singleOf(::MediaPlayerRepositoryImpl)
}