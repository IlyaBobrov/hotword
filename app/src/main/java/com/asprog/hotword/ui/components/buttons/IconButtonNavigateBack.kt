package com.asprog.hotword.ui.components.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun IconButtonNavigateBack(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "Navigate back"
        )
    }
}