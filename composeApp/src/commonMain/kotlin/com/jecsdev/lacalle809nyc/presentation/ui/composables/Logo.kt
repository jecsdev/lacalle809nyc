package com.jecsdev.lacalle809nyc.presentation.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.lc809_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun Logo() {
    Image(
        painter = painterResource(Res.drawable.lc809_logo),
        modifier = Modifier.size(120.dp),
        contentDescription = null
    )
}