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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.entity.Screen
import com.asprog.hotword.data.sample.PlayerName
import com.asprog.hotword.interfaces.Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreen(
    callback: (CreateGameNavigation) -> Unit
) {
    val players = remember { mutableStateListOf<Player>() }

    val addPlayer: () -> Unit = {
        val id = players.size
        val name = PlayerName.listNames[id % PlayerName.listNames.size]
        val player = Player(id, name)
        players.add(player)
    }

    LaunchedEffect(Unit) {
        addPlayer()
        addPlayer()
    }

    val startGame: () -> Unit = {
        callback(CreateGameNavigation.StartRound)
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
            Text(text = "С кем играем")

            players.forEach { player ->
                ItemPlayer(playerData = player) {
                    players.remove(player)
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

sealed interface CreateGameNavigation : Navigation {
    data object StartRound : CreateGameNavigation
}

fun createGameNavigationAction(
    navigator: CreateGameNavigation,
    navController: NavHostController
) {
    when (navigator) {
        CreateGameNavigation.StartRound -> {
            navController.navigate(Screen.StartGameRound.route)
        }
    }
}