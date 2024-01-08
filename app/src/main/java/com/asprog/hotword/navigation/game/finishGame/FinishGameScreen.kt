package com.asprog.hotword.navigation.game.finishGame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishGameScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    LaunchedEffect(Unit) {
        events(GameEvent.FinishGame.Init)
    }

    val goToLobby = {
        navigate(NavRouts.FromFinishGame.ToLobbyGame)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Вот и конец!") })
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(text = "Список победителей")
            uiState.players.sortedBy { it.count }.reversed().forEach { player ->
                ItemPlayer(player)
            }
            Button(onClick = goToLobby) {
                Text(text = "Завершить игру")
            }
        }
    }
}


@Composable
private fun ItemPlayer(
    playerData: Player,
) {
    Row(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = playerData.name)
        Text(text = playerData.count.toString())
    }
}