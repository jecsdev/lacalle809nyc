package com.jecsdev.lacalle809nyc.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.lacalle_logo_editorial_v2
import lacalle809nyc.composeapp.generated.resources.radio_logo

import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.lacalle_logo_editorial_v2),
        modifier = modifier,
        contentScale = ContentScale.Fit,
        contentDescription = stringResource(Res.string.radio_logo)
    )
}
