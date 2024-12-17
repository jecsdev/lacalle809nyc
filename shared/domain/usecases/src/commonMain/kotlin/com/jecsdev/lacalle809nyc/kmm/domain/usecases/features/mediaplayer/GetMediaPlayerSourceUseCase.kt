package com.jecsdev.lacalle809nyc.kmm.domain.usecases.features.mediaplayer

import com.jecsdev.lacalle809nyc.kmm.domain.repository.features.MediaPlayerRepository

class GetMediaPlayerSourceUseCase(private val mediaPlayerRepository: MediaPlayerRepository) {
    operator fun invoke(): String = mediaPlayerRepository.getMediaUrl()
}