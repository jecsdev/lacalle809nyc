package com.jecsdev.lacalle809nyc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.jecsdev.lacalle809nyc.presentation.theme.AppMaterialTheme
import com.jecsdev.lacalle809nyc.presentation.ui.composables.Logo
import com.jecsdev.lacalle809nyc.presentation.ui.composables.PlayPauseButton
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.app_name
import lacalle809nyc.composeapp.generated.resources.best_music_experience
import org.jetbrains.compose.resources.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
@Composable
@org.jetbrains.compose.ui.tooling.preview.Preview
fun App() {
    AppMaterialTheme {
        Scaffold { paddingValues ->
            var isPlaying by rememberSaveable { mutableStateOf(false) }
            val context = LocalContext.current
            var exoPlayer: ExoPlayer? = remember { ExoPlayer.Builder(context).build() }

            // Lógica para iniciar el video/audio (reemplazar con tu URL o archivo local)
            val mediaItem = remember {
                MediaItem.fromUri(BuildConfig.BASE_URL) // Cambia la URL
            }

            // Configurar ExoPlayer cuando se cambia el estado de la reproducción
            LaunchedEffect(isPlaying) {
                if (isPlaying) {
                    exoPlayer?.setMediaItem(mediaItem)
                    exoPlayer?.prepare()
                    exoPlayer?.play()
                } else {
                    exoPlayer?.pause()
                }
            }

            // UI
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

            // Liberar ExoPlayer cuando ya no se use
            DisposableEffect(exoPlayer) {
                onDispose {
                    exoPlayer?.release()
                }
            }
        }
    }
}
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}