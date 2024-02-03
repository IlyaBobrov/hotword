package com.asprog.hotword.navigation.game.endGame

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.theme.HotWordTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EndGameScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    val players = uiState.players

    var losePlayer by remember { mutableStateOf<Player?>(null) }

    val playerLose: (Player) -> Unit = { player ->
        losePlayer = if (player == losePlayer) null else player
    }

    val isLastRound = uiState.currentRound > uiState.maxRounds

    LaunchedEffect(Unit) {
        events(GameEvent.EndGame.Init)
    }

    val nextButtonAction: () -> Unit = {
        if (isLastRound) {
            events(GameEvent.EndGame.LosePlayer(losePlayer!!))
            navigate(NavRouts.FromEndGame.ToFinishGame)
        } else {
            events(GameEvent.EndGame.LosePlayer(losePlayer!!))
            navigate(NavRouts.FromEndGame.ToStartGame)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "БУМ!") })
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(0.dp),
        ) {
            Text(
                text = "Упс, кто то взлетел на воздух",
                modifier = Modifier
                    .padding(16.dp)
            )

            players.forEach { playerData ->
                ItemPlayer(playerData = playerData, losePlayer = losePlayer) {
                    playerLose(playerData)
                }
            }

            Button(
                onClick = nextButtonAction,
                enabled = losePlayer != null,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = if (isLastRound) "Итоги игры" else "Следующий раунд")
            }
        }
    }
}


@Composable
private fun ItemPlayer(
    playerData: Player,
    losePlayer: Player?,
    clickAction: () -> Unit
) {
    Row(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
            .clickable { clickAction() }
            .background(if (playerData == losePlayer) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = playerData.name)
        Text(text = if (playerData == losePlayer) (playerData.count + 1).toString() else playerData.count.toString())
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SBlockChangePreview() {
    HotWordTheme {
        ItemPlayer(Player(1), Player(1)) {}
    }
}
