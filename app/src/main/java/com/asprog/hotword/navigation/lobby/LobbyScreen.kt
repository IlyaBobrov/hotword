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
import androidx.navigation.NavHostController
import com.asprog.hotword.R
import com.asprog.hotword.data.entity.Screen
import com.asprog.hotword.interfaces.Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(
    callback: (LobbyNavigation) -> Unit
) {
    val startGame = { callback(LobbyNavigation.StartGame) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = startGame) {
                Text(text = "Начать игру")
            }
        }
    }
}

sealed interface LobbyNavigation : Navigation {
    data object StartGame : LobbyNavigation
}

fun lobbyNavigationAction(
    navigator: LobbyNavigation,
    navController: NavHostController
) {
    when (navigator) {
        LobbyNavigation.StartGame -> {
            navController.navigate(Screen.PlayNavGraph.route)
        }
    }
}