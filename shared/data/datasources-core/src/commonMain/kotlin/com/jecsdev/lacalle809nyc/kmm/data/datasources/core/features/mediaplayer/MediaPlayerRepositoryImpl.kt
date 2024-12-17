package com.jecsdev.lacalle809nyc.kmm.data.datasources.core.features.mediaplayer

import com.jecsdev.lacalle809nyc.kmm.data.datasources.core.remote.ApiService
import com.jecsdev.lacalle809nyc.kmm.domain.repository.features.MediaPlayerRepository

class MediaPlayerRepositoryImpl : MediaPlayerRepository {
    override fun getMediaUrl(): String {
        return ApiService.SOURCE_URL
    }
}