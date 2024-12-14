package io.jecsdev.lacalle809nyc.kmm.core.di

import io.jecsdev.lacalle809nyc.kmm.core.common.di.KoinModuleLoader
import io.jecsdev.lacalle809nyc.kmm.data.datasources.core.di.DataSourceCoreDependencyInjector
import io.jecsdev.lacalle809nyc.kmm.data.repository.di.DataRepositoryDependencyInjector
import io.jecsdev.lacalle809nyc.kmm.domain.usecases.di.DomainUseCasesDependencyInjector
import io.jecsdev.lacalle809nyc.kmm.presentation.viewmodels.di.PresentationViewModelsDependencyInjector
import org.koin.core.module.Module

object CoreDiDependencyInjector : KoinModuleLoader {
    override val modules: List<Module>
        get() = listOf(
            DataSourceCoreDependencyInjector.modules,
            DataRepositoryDependencyInjector.modules,
            DomainUseCasesDependencyInjector.modules,
            PresentationViewModelsDependencyInjector.modules,
        ).flatten()
}
