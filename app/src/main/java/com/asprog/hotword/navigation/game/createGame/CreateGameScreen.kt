package com.asprog.hotword.navigation.game.createGame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.sample.PlayerName
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.components.buttons.IconButtonNavigateBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    val players = uiState.players

    val addPlayer: () -> Unit = {
        val playersNew = players.toMutableList()
        val id = playersNew.size
        val name = PlayerName.listNames[id % PlayerName.listNames.size]
        val player = Player(id, name)
        playersNew.add(player)

        events(GameEvent.CreateScreen.SetPersons(playersNew))
    }

    val removePlayer: (player: Player) -> Unit = { player: Player ->
        val playersNew = players.toMutableList()
        playersNew.remove(player)

        events(GameEvent.CreateScreen.SetPersons(playersNew))
    }

    val startGame: () -> Unit = {
        navigate(NavRouts.FromCreateGame.ToStartGame)
    }

    val settingsGame: () -> Unit = {
        navigate(NavRouts.FromCreateGame.ToSettings)
    }

    val toLobby: () -> Unit = {
        navigate(NavRouts.FromCreateGame.ToLobby)
    }

    LaunchedEffect(Unit) {
        events(GameEvent.CreateScreen.Init)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Подготвка к игре") },
                navigationIcon = { IconButtonNavigateBack(toLobby) },
                actions = {
                    IconButton(onClick = settingsGame) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text(text = "С кем играем")

            players.forEach { player ->
                ItemPlayer(playerData = player) {
                    removePlayer(player)
                }
            }
            Button(onClick = addPlayer) {
                Text(text = "Добавить игрока")
            }

            Button(onClick = startGame) {
                Text(text = "Начать игру")
            }
        }
    }
}

@Composable
fun ItemPlayer(
    playerData: Player,
    removeAction: () -> Unit
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
        IconButton(onClick = { removeAction() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Удалить")
        }
    }
}