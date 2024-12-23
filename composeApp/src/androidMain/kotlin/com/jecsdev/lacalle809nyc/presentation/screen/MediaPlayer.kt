package com.jecsdev.lacalle809nyc.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jecsdev.lacalle809nyc.BuildConfig
import com.jecsdev.lacalle809nyc.presentation.theme.AppMaterialTheme
import com.jecsdev.lacalle809nyc.presentation.ui.composables.Logo
import com.jecsdev.lacalle809nyc.presentation.ui.composables.PlayPauseButton
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.app_name
import lacalle809nyc.composeapp.generated.resources.best_music_experience
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun MediaPlayer() {
    AppMaterialTheme {
        Scaffold { paddingValues ->
            var isPlaying by rememberSaveable { mutableStateOf(false) }
            val context = LocalContext.current
            val exoPlayer: ExoPlayer = remember { ExoPlayer.Builder(context).build() }

            val mediaItem = remember {
                MediaItem.fromUri(BuildConfig.BASE_URL)
            }

            LaunchedEffect(isPlaying) {
                if (isPlaying) {
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.play()
                } else {
                    exoPlayer.pause()
                }
            }

            Column(
                Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        text = stringResource(Res.string.best_music_experience),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Logo()
                }

                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PlayPauseButton(isPlaying = isPlaying) {
                        isPlaying = !isPlaying
                    }
                }
            }

            DisposableEffect(exoPlayer) {
                onDispose {
                    exoPlayer.release()
                }
            }
        }
    }
}