package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.di

import io.jecsdev.lacalle809nyc.kmm.core.common.di.KoinModuleLoader
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.db.Database
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.preferences.AppPreferencesImpl
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.remote.ApiService
import com.jecsdev.lacalle809nyc.kmm.data.datasources.features.preferences.AppPreferences
import de.jensklingenberg.ktorfit.Ktorfit
import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.remote.createApiService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect fun getPlatformInjects(): List<Module>

object DataSourceCoreDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = getPlatformInjects().union(
            listOf(
                module {
                    single<ApiService> {
                        Ktorfit
                            .Builder()
                            .baseUrl(ApiService.API_URL)
                            .build()
                            .createApiService()
                    }
                    singleOf(::Database)
                    single<AppPreferences> { AppPreferencesImpl(get()) }
                }
            )
        ).toList()
}
