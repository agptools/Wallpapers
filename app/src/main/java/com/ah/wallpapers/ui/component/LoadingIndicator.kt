package com.ah.wallpapers.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FullscreenLoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        LoadingIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp)
        )
    }
}