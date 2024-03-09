package com.asprog.hotword.navigation.game.createGame

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.sample.PlayerName
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.components.buttons.IconButtonNavigateBack
import com.asprog.hotword.ui.theme.HotWordTheme
import com.asprog.tools_kit.ui.compose.spacers.SpacerVertical

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    val players = uiState.players

    val isNewGame = uiState.currentRound == 1

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

    val clearGame: () -> Unit = {
        events(GameEvent.CreateScreen.ClearGame)
    }

    LaunchedEffect(Unit) {
        events(GameEvent.CreateScreen.Init)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Подготовка к игре") },
                navigationIcon = { IconButtonNavigateBack(toLobby) },
                actions = {
                    IconButton(onClick = settingsGame) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(),
            ) {
                Text(text = "Список игроков", style = MaterialTheme.typography.titleMedium)
                SpacerVertical(height = 16.dp)
                if (players.isEmpty()) {
                    Text(
                        text = "Нажмите кнопку \"Добавить игрока\"",
                        style = MaterialTheme.typography.labelMedium,
                    )
                } else {
                    players.forEach { player ->
                        ItemPlayer(playerData = player) {
                            removePlayer(player)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = startGame, enabled = players.isNotEmpty()) {
                    val text = if (isNewGame) "Начать игру" else "Продолжить игру"
                    Text(text = text)
                }

                SpacerVertical(height = 16.dp)

                if (!isNewGame) {
                    Button(onClick = clearGame) {
                        Text(text = "Сбросить прошлую игру")
                    }

                    SpacerVertical(height = 16.dp)
                }

                Button(onClick = addPlayer, enabled = isNewGame) {
                    Text(text = "Добавить игрока")
                }
                SpacerVertical(height = 16.dp)

                Button(onClick = settingsGame, enabled = isNewGame) {
                    Text(text = "Настройки")
                }

                SpacerVertical(height = 64.dp)
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
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Удалить ${playerData.name}"
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SBlockChangePreview() {
    HotWordTheme {
        CreateGameScreen(GameUiState(), {}, {})
    }
}
