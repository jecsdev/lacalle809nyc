package com.jecsdev.lacalle809nyc.kmm.domain.usecases.features.preferences

import com.jecsdev.lacalle809nyc.kmm.domain.repository.features.preferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetDeviceIdUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): Flow<Long> = this.repository.getDeviceId()
}