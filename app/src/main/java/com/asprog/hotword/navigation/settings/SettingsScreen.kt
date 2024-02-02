package com.asprog.hotword.navigation.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asprog.hotword.data.sample.Const
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.components.buttons.IconButtonNavigateBack
import com.asprog.hotword.ui.theme.HotWordTheme
import com.asprog.tools_kit.ui.compose.spacers.SpacerVertical
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    val backToCreateGame: () -> Unit = {
        navigate(NavRouts.FromSettings.ToCreateGame)
    }

    val minTime = (uiState.minTimeRound / Const.second).toInt()
    val maxTime = (uiState.maxTimeRound / Const.second).toInt()

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
            BlockChange(
                title = "Количество раундов",
                count = uiState.maxRounds,
                step = 1
            ) { newValue ->
                events(GameEvent.Settings.RoundCountUpdate(max(1, newValue)))
            }
            SpacerVertical(16.dp)
            BlockChange(
                title = "Минимальное время раунда (сек)",
                count = minTime,
                step = 5
            ) { newValue ->
                if (newValue < maxTime)
                    events(
                        GameEvent.Settings.MinTimeRoundUpdate(
                            max(5, newValue) * Const.second
                        )
                    )
            }
            SpacerVertical(16.dp)

            BlockChange(
                title = "Максимальное время раунда (сек)",
                count = maxTime,
                step = 5
            ) { newValue ->
                if (newValue > minTime)
                    events(
                        GameEvent.Settings.MaxTimeRoundUpdate(
                            max(10, newValue) * Const.second
                        )
                    )
            }
            SpacerVertical(16.dp)
            BlockBoolean(title = "Отображение таймера", uiState.showTimer) { newValue ->
                events(GameEvent.Settings.ShowTimerUpdate(newValue))
            }
        }
    }
}

@Composable
private fun BlockChange(title: String, count: Int, step: Int = 1, changeAction: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .weight(3F)
                .padding(end = 16.dp)
        )
        Row(
            modifier = Modifier.weight(2F),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    changeAction(count - step)
                },
                modifier = Modifier
                    .size(48.dp)
            ) {
                Text(text = "-")
            }
            Text(
                text = count.toString(),
                modifier = Modifier.width(48.dp),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = {
                    changeAction(count + step)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Text(text = "+")
            }
        }
    }
}

@Composable
private fun BlockBoolean(title: String, value: Boolean, changeAction: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(3F)
        )
        Button(
            onClick = { changeAction(!value) },
            modifier = Modifier.weight(2F)
        ) {
            Text(
                text = if (value) "Включено" else "Отключено",
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SBlockChangePreview() {
    HotWordTheme {
        BlockChange("Раунды", 5, 1, {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    HotWordTheme {
        SettingsScreen(uiState = GameUiState(), {}) {}
    }
}