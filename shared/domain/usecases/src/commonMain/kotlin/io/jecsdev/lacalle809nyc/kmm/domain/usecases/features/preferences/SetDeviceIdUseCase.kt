package io.jecsdev.lacalle809nyc.kmm.domain.usecases.features.preferences

import io.jecsdev.lacalle809nyc.kmm.domain.repository.features.preferences.PreferencesRepository

class SetDeviceIdUseCase(private val repository: PreferencesRepository) {
    suspend operator fun invoke(deviceId: Long) = this.repository.setDeviceId(deviceId)
}
