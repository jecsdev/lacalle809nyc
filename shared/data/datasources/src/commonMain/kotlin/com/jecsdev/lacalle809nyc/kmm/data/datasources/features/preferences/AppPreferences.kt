package com.jecsdev.lacalle809nyc.kmm.data.datasources.features.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferences {
    fun getDeviceId(): Flow<Long>
    suspend fun setDeviceId(deviceId: Long)
}
