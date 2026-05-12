package com.jecsdev.lacalle809nyc.presentation.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.jecsdev.lacalle809nyc.BuildConfig
import com.jecsdev.lacalle809nyc.R
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
            val context = LocalContext.current
            val audioManager =
                remember { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

            var isPlaying by rememberSaveable { mutableStateOf(false) }
            var volume by rememberSaveable {
                mutableFloatStateOf(
                    audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() /
                            audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                )
            }

            val exoPlayer: ExoPlayer = remember { ExoPlayer.Builder(context).build() }

            val mediaItem = remember {
                MediaItem.fromUri(BuildConfig.BASE_URL)
            }

            // Sync with system volume changes
            DisposableEffect(context) {
                val filter = IntentFilter("android.media.VOLUME_CHANGED_ACTION")
                val receiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                        volume = currentVolume.toFloat() / maxVolume
                    }
                }
                context.registerReceiver(receiver, filter)
                onDispose {
                    context.unregisterReceiver(receiver)
                }
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

            LaunchedEffect(volume) {
                exoPlayer.volume = volume
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
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
                    Spacer(modifier = Modifier.height(36.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PlayPauseButton(isPlaying = isPlaying) {
                        isPlaying = !isPlaying
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeDown,
                            contentDescription = androidx.compose.ui.res.stringResource(R.string.volume_down),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Slider(
                            value = volume,
                            onValueChange = { newVolume ->
                                volume = newVolume
                                val maxVolume =
                                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                val targetVolume = (newVolume * maxVolume).toInt()
                                audioManager.setStreamVolume(
                                    AudioManager.STREAM_MUSIC,
                                    targetVolume,
                                    0 // No flag to avoid showing system dialog twice
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = androidx.compose.ui.res.stringResource(R.string.volume_up),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            androidx.compose.ui.res.stringResource(R.string.app_disclaimer),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
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
