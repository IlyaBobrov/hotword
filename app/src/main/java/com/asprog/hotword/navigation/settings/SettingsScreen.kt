package com.asprog.hotword.navigation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.components.buttons.IconButtonNavigateBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigate: (NavRouts) -> Unit
) {
    val backToCreateGame: () -> Unit = {
        navigate(NavRouts.FromSettings.ToCreateGame)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Настройки игры") },
                navigationIcon = { IconButtonNavigateBack(backToCreateGame) }
            )
        },
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(text = "В разработке")
        }
    }
}