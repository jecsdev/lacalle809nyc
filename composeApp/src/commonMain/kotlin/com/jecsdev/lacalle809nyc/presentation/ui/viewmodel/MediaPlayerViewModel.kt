package com.jecsdev.lacalle809nyc.presentation.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jecsdev.lacalle809nyc.domain.repository.MediaPlayerRepository
import com.jecsdev.lacalle809nyc.presentation.ui.state.MediaPlayerState

class MediaPlayerViewModel(private val mediaPlayerRepository: MediaPlayerRepository) : ViewModel() {
    var state by mutableStateOf(MediaPlayerState())
        private set


    init {
        state = state.copy(isPlaying = true)
    }

    fun playPause() {
        state = state.copy(isPlaying = !state.isPlaying)
    }

}