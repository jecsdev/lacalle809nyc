package com.jecsdev.lacalle809nyc.presentation.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.SystemClock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.jecsdev.lacalle809nyc.BuildConfig
import com.jecsdev.lacalle809nyc.R
import com.jecsdev.lacalle809nyc.presentation.theme.AppMaterialTheme
import com.jecsdev.lacalle809nyc.presentation.ui.composables.Logo
import kotlinx.coroutines.delay
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.app_name
import lacalle809nyc.composeapp.generated.resources.best_music_experience
import org.jetbrains.compose.resources.stringResource
import kotlin.math.min

private val liveRed = Color(0xFFE52346)
private val playerCyan = Color(0xFF2DC7EF)

@Composable
fun MediaPlayer() {
    AppMaterialTheme {
        RadioPlayerScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RadioPlayerScreen() {
    val context = LocalContext.current
    val audioManager = remember(context) {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }
    val player = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(BuildConfig.BASE_URL))
        }
    }

    var playbackState by remember { mutableIntStateOf(player.playbackState) }
    var isPlaying by remember { mutableStateOf(player.isPlaying) }
    var playRequested by remember { mutableStateOf(player.playWhenReady) }
    var playbackError by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(audioManager.volumeFraction()) }
    var showTimerDialog by remember { mutableStateOf(false) }
    var timerEndElapsed by rememberSaveable { mutableLongStateOf(0L) }
    var timerRemainingSeconds by remember { mutableLongStateOf(0L) }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                playbackState = state
                if (state == Player.STATE_READY) playbackError = false
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                playRequested = playWhenReady
            }

            override fun onPlayerError(error: PlaybackException) {
                playbackError = true
                playRequested = false
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    DisposableEffect(context, audioManager) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                volume = audioManager.volumeFraction()
            }
        }
        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter("android.media.VOLUME_CHANGED_ACTION"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        onDispose { context.unregisterReceiver(receiver) }
    }

    LaunchedEffect(timerEndElapsed) {
        while (timerEndElapsed > 0L) {
            val remaining = ((timerEndElapsed - SystemClock.elapsedRealtime()) / 1000L).coerceAtLeast(0L)
            timerRemainingSeconds = remaining
            if (remaining == 0L) {
                player.pause()
                timerEndElapsed = 0L
                break
            }
            delay(1000L)
        }
        if (timerEndElapsed == 0L) timerRemainingSeconds = 0L
    }

    val connecting = playRequested &&
        !playbackError &&
        (playbackState == Player.STATE_BUFFERING || playbackState == Player.STATE_IDLE)

    val onPlayPause = {
        if (isPlaying || connecting) {
            player.pause()
        } else {
            if (playbackError || playbackState == Player.STATE_IDLE) {
                playbackError = false
                player.prepare()
            }
            player.play()
        }
    }

    val onShare = {
        val shareText = context.getString(
            R.string.share_message,
            context.getString(R.string.play_store_url)
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_title)))
    }

    val colors = MaterialTheme.colorScheme
    val isDark = isSystemInDarkTheme()
    val secondarySurface = if (isDark) Color(0xFF192437) else Color(0xFFE6F4FC)
    val mutedText = if (isDark) Color(0xFFC8D2E3) else Color(0xFF253D5C)

    val identity: @Composable (Boolean, Modifier) -> Unit = { compact, modifier ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(if (compact) 4.dp else 8.dp)
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = if (compact) MaterialTheme.typography.headlineSmall
                else MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = colors.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(Res.string.best_music_experience),
                style = if (compact) MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = mutedText
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LogoAtmosphere(isDark, Modifier.fillMaxSize())
                Logo(Modifier.fillMaxSize())
            }
        }
    }

    val controls: @Composable (Boolean, Modifier) -> Unit = { compact, modifier ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (compact) Arrangement.SpaceBetween
            else Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = liveRed.copy(alpha = if (isDark) 0.16f else 0.1f),
                border = BorderStroke(1.dp, liveRed.copy(alpha = 0.7f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 17.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Box(
                        Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(liveRed)
                    )
                    Text(
                        text = context.getString(R.string.live),
                        color = if (isDark) Color.White else Color(0xFFB20D2C),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Text(
                text = context.getString(R.string.app_disclaimer),
                style = if (compact) MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.titleMedium,
                color = colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier.size(if (compact) 72.dp else 96.dp),
                contentAlignment = Alignment.Center
            ) {
                PlayerGlow(isDark, Modifier.fillMaxSize())
                Button(
                    onClick = onPlayPause,
                    modifier = Modifier.fillMaxSize(),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = playerCyan,
                        contentColor = Color(0xFF081C2D)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    if (connecting) {
                        CircularProgressIndicator(
                            color = Color(0xFF081C2D),
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(if (compact) 30.dp else 38.dp)
                        )
                    } else {
                        Icon(
                            imageVector = when {
                                playbackError -> Icons.Default.Refresh
                                isPlaying -> Icons.Default.Pause
                                else -> Icons.Default.PlayArrow
                            },
                            contentDescription = when {
                                playbackError -> context.getString(R.string.retry)
                                isPlaying -> context.getString(R.string.pause)
                                else -> context.getString(R.string.play)
                            },
                            modifier = Modifier.size(if (compact) 38.dp else 48.dp)
                        )
                    }
                }
            }

            Text(
                text = when {
                    playbackError -> context.getString(R.string.playback_error)
                    connecting -> context.getString(R.string.connecting)
                    isPlaying -> context.getString(R.string.listening_live)
                    else -> context.getString(R.string.tap_to_listen)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (playbackError) colors.error else mutedText,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SecondaryAction(
                    label = if (timerEndElapsed > 0L) {
                        context.getString(
                            R.string.timer_remaining,
                            timerRemainingSeconds / 60L,
                            timerRemainingSeconds % 60L
                        )
                    } else {
                        context.getString(R.string.sleep_timer)
                    },
                    icon = Icons.Default.Bedtime,
                    surface = secondarySurface,
                    contentColor = mutedText,
                    compact = compact,
                    onClick = { showTimerDialog = true }
                )
                SecondaryAction(
                    label = context.getString(R.string.share),
                    icon = Icons.Default.Share,
                    surface = secondarySurface,
                    contentColor = mutedText,
                    compact = compact,
                    onClick = onShare
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeDown,
                    contentDescription = null,
                    tint = mutedText,
                    modifier = Modifier.size(24.dp)
                )
                Slider(
                    value = volume,
                    onValueChange = { newVolume ->
                        volume = newVolume
                        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                        audioManager.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            (newVolume * maxVolume).toInt(),
                            0
                        )
                    },
                    thumb = {
                        Box(
                            Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    if (isDark) Color(0xFF9BDFFF) else Color(0xFF9DBBD2),
                                    CircleShape
                                )
                        )
                    },
                    track = { sliderState ->
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(if (isDark) Color(0xFF344760) else Color(0xFFC8DFF0))
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth(sliderState.value.coerceIn(0f, 1f))
                                    .fillMaxHeight()
                                    .background(playerCyan)
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .semantics { contentDescription = context.getString(R.string.volume) }
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = null,
                    tint = mutedText,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }

    Scaffold(containerColor = colors.background) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val useTwoPanes = maxWidth > maxHeight && maxWidth >= 480.dp
            val compact = maxHeight < 620.dp
            val veryShort = maxHeight < 280.dp
            if (useTwoPanes) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    identity(compact, Modifier.weight(1f).fillMaxHeight())
                    val controlsModifier = Modifier.weight(1f).fillMaxHeight()
                    controls(
                        compact,
                        if (veryShort || LocalDensity.current.fontScale > 1.3f) {
                            controlsModifier.verticalScroll(rememberScrollState())
                        } else {
                            controlsModifier
                        }
                    )
                }
            } else if (maxHeight < 520.dp || LocalDensity.current.fontScale > 1.3f) {
                // A very short window or large text needs an overflow path to keep every control reachable.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    identity(true, Modifier.fillMaxWidth().height(220.dp))
                    controls(true, Modifier.fillMaxWidth())
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(if (compact) 4.dp else 8.dp)
                ) {
                    identity(compact, Modifier.fillMaxWidth().weight(1f))
                    controls(compact, Modifier.fillMaxWidth())
                }
            }
        }
    }

    if (showTimerDialog) {
        AlertDialog(
            onDismissRequest = { showTimerDialog = false },
            title = { Text(context.getString(R.string.sleep_timer)) },
            text = {
                Column(
                    modifier = Modifier
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    listOf(
                        15 to context.getString(R.string.timer_minutes, 15),
                        30 to context.getString(R.string.timer_minutes, 30),
                        60 to context.getString(R.string.timer_one_hour),
                        90 to context.getString(R.string.timer_one_hour_half),
                        120 to context.getString(R.string.timer_two_hours),
                        150 to context.getString(R.string.timer_two_hours_half),
                        180 to context.getString(R.string.timer_three_hours)
                    ).forEach { (minutes, label) ->
                        TextButton(
                            onClick = {
                                timerEndElapsed = SystemClock.elapsedRealtime() + minutes * 60_000L
                                showTimerDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(label)
                        }
                    }
                    if (timerEndElapsed > 0L) {
                        TextButton(
                            onClick = {
                                timerEndElapsed = 0L
                                showTimerDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(context.getString(R.string.cancel_timer))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTimerDialog = false }) {
                    Text(context.getString(R.string.close))
                }
            }
        )
    }
}

@Composable
private fun LogoAtmosphere(isDark: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val center = Offset(size.width / 2f, size.height * 0.52f)
        val dimension = min(size.width, size.height)
        val glowRadius = dimension * 0.78f
        drawCircle(
            brush = Brush.radialGradient(
                0f to playerCyan.copy(alpha = if (isDark) 0.19f else 0.11f),
                0.55f to playerCyan.copy(alpha = if (isDark) 0.08f else 0.04f),
                1f to Color.Transparent,
                center = center,
                radius = glowRadius
            ),
            radius = glowRadius,
            center = center
        )

        repeat(3) { index ->
            val radius = dimension * (0.48f + index * 0.09f)
            val arcColor = playerCyan.copy(
                alpha = (if (isDark) 0.48f else 0.32f) / (index + 1)
            )
            val topLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(radius * 2f, radius * 2f)
            val stroke = Stroke(width = (if (index == 0) 1.7.dp else 1.2.dp).toPx())
            drawArc(arcColor, 125f, 110f, false, topLeft, arcSize, style = stroke)
            drawArc(arcColor, -55f, 110f, false, topLeft, arcSize, style = stroke)
        }
    }
}

@Composable
private fun PlayerGlow(isDark: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = 82.dp.toPx()
        drawCircle(
            brush = Brush.radialGradient(
                0f to playerCyan.copy(alpha = if (isDark) 0.35f else 0.18f),
                0.55f to playerCyan.copy(alpha = if (isDark) 0.16f else 0.08f),
                1f to Color.Transparent,
                center = center,
                radius = radius
            ),
            radius = radius,
            center = center
        )
    }
}

@Composable
private fun SecondaryAction(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    surface: Color,
    contentColor: Color,
    compact: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(if (compact) 2.dp else 6.dp)
    ) {
        Surface(
            onClick = onClick,
            modifier = Modifier.size(if (compact) 48.dp else 58.dp),
            shape = CircleShape,
            color = surface,
            border = BorderStroke(1.dp, contentColor.copy(alpha = 0.2f))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = contentColor,
                    modifier = Modifier.size(if (compact) 23.dp else 27.dp)
                )
            }
        }
        Text(
            text = label,
            color = contentColor,
            fontSize = if (compact) 12.sp else 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

private fun AudioManager.volumeFraction(): Float {
    val maxVolume = getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    return if (maxVolume > 0) {
        getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / maxVolume
    } else {
        0f
    }
}
