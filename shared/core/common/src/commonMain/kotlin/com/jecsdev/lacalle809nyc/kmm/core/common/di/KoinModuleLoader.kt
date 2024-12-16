package io.jecsdev.lacalle809nyc.kmm.core.common.di

import org.koin.core.module.Module

interface KoinModuleLoader {
    val modules: List<Module>
}