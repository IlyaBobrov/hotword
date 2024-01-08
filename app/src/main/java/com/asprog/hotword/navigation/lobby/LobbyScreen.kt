package com.asprog.hotword.navigation.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asprog.hotword.R
import com.asprog.hotword.navigation.controller.NavRouts

@Composable
fun LobbyScreen(
    navigate: (NavRouts) -> Unit
) {
    val createGame = { navigate(NavRouts.FromLobby.ToCreateGame) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(vertical = 32.dp),
                fontSize = 24.sp
            )
            Button(onClick = createGame) {
                Text(text = "Начать игру")
            }
        }
    }
}