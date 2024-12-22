package com.jecsdev.lacalle809nyc.data.repository

import com.jecsdev.lacalle809nyc.domain.repository.MediaPlayerRepository

class MediaPlayerRepositoryImpl(private val mediaUrl: String) : MediaPlayerRepository {
    override fun getMediaUrl(): String {
        return mediaUrl
    }
}