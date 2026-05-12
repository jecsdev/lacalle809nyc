package com.jecsdev.lacalle809nyc.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.lacalle_logo
import lacalle809nyc.composeapp.generated.resources.radio_logo

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Logo() {
    Image(
        painter = painterResource(Res.drawable.lacalle_logo),
        modifier = Modifier.height(240.dp)
            .width(360.dp),
        contentDescription = stringResource(Res.string.radio_logo)
    )
}