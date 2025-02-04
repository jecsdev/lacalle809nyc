package com.jecsdev.lacalle809nyc.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jecsdev.lacalle809nyc.presentation.theme.AppMaterialTheme
import lacalle809nyc.composeapp.generated.resources.Res
import lacalle809nyc.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    AppMaterialTheme {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(Res.string.app_name),
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}
