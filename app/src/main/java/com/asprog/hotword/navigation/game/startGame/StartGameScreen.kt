package com.asprog.hotword.navigation.game.startGame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts

@Composable
fun StartGameScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    LaunchedEffect(Unit) {
        events(GameEvent.StartGame.Init)
    }

    val runGame: () -> Unit = {
        navigate(NavRouts.FromStartGame.ToRunGame)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Раунд " + (uiState.currentRound))
            Button(onClick = runGame) {
                Text(text = "Начать игру")
            }
        }
    }
}