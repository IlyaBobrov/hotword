package com.asprog.hotword.navigation.game.finishGame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.sample.PlayerName
import com.asprog.hotword.interfaces.Navigation
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.navigation.game.data.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishGameScreen(
    navigate: (NavRouts) -> Unit
) {
    val players = remember { mutableStateListOf<Player>() }

    val addPlayer: () -> Unit = {
        val id = players.size
        val name = PlayerName.listNames[id % PlayerName.listNames.size]
        val player = Player(id, name)
        players.add(player)
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

            Button(onClick = addPlayer) {
                Text(text = "Добавить игрока")
            }
        }
    }
}