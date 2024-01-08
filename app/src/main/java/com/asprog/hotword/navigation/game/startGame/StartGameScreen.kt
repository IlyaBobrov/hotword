package com.asprog.hotword.navigation.game.startGame

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartGameScreen(
    navigate: (NavRouts) -> Unit
) {

    val runGame: () -> Unit = {
        navigate(NavRouts.FromStartGame.ToRunGame)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Подготвка к игре") })
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Button(onClick = runGame) {
                Text(text = "Начать игру")
            }
        }
    }
}