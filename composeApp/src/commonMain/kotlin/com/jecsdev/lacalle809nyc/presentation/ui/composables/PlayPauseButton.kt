package com.jecsdev.lacalle809nyc.presentation.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.pause
import lacalle809nyc.composeapp.generated.resources.play
import lacalle809nyc.composeapp.generated.resources.radio_logo
import org.jetbrains.compose.resources.stringResource

@Composable
fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = { onClick() }) {
        if (isPlaying) {
            Icon(imageVector = Icons.Default.Pause, contentDescription =  stringResource(Res.string.pause))

        } else {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = stringResource(Res.string.play))
        }

    }
}

