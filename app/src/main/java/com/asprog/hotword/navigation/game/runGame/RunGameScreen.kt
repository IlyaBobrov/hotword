package com.asprog.hotword.navigation.game.runGame

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameUiState
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.ui.theme.HotWordTheme

@Composable
fun RunGameScreen(
    uiState: GameUiState,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    LaunchedEffect(Unit) {
        events(GameEvent.RunGame.Init)
    }

    LaunchedEffect(uiState.boom) {
        if (uiState.boom) {
            navigate(NavRouts.FromRunGame.ToEndGame)
        }
    }

    BackHandler {

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Box(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(5f)
            ) {
                Text(
                    text = uiState.currentWord,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            if (uiState.showTimer) {
                val context = LocalContext.current

                val displayMetrics = context.resources.displayMetrics

                val sc = uiState.currentTimer.toDouble() / uiState.currentTimerInit.toDouble()

                val dpWidthPhone =
                    (displayMetrics.widthPixels.toDouble() / displayMetrics.density.toDouble()) * sc
                val dpHeightPhone =
                    (displayMetrics.heightPixels.toDouble() / displayMetrics.density.toDouble()) * sc

                Box(
                    modifier = Modifier
                        .size(dpWidthPhone.dp, dpHeightPhone.dp)
                        .background(Color.Yellow)
                        .align(Alignment.Center)
                        .zIndex(4f)
                )
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SBlockChangePreview() {
    HotWordTheme {
        RunGameScreen(GameUiState(), {}, {})
    }
}
