package com.asprog.hotword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.asprog.hotword.data.entity.Screen
import com.asprog.hotword.data.viewModel.GameEvent
import com.asprog.hotword.data.viewModel.GameViewModel
import com.asprog.hotword.navigation.controller.NavRouts
import com.asprog.hotword.navigation.controller.globalNavigationGraph
import com.asprog.hotword.navigation.game.createGame.CreateGameScreen
import com.asprog.hotword.navigation.game.endGame.EndGameScreen
import com.asprog.hotword.navigation.game.finishGame.FinishGameScreen
import com.asprog.hotword.navigation.game.runGame.RunGameScreen
import com.asprog.hotword.navigation.game.startGame.StartGameScreen
import com.asprog.hotword.navigation.lobby.LobbyScreen
import com.asprog.hotword.navigation.settings.SettingsScreen
import com.asprog.hotword.ui.theme.HotWordTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

            AppContainer(navController)
        }
    }
}

@Composable
fun AppContainer(navController: NavHostController) {
    HotWordTheme {
        val viewModel: GameViewModel = hiltViewModel()
        val navigation = { rout: NavRouts -> globalNavigationGraph(rout, navController) }
        val events = { event: GameEvent -> viewModel.onEvent(event) }

        NavHost(
            navController = navController,
            startDestination = Screen.LobbyNavGraph.route,
            route = Screen.App.route,
        ) {
            lobbyNavGraph(
                viewModel = viewModel,
                events = events,
                navigate = navigation,
            )
        }
    }
}

fun NavGraphBuilder.lobbyNavGraph(
    viewModel: GameViewModel,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    val startDestination = Screen.Lobby.route

    navigation(
        route = Screen.LobbyNavGraph.route,
        startDestination = startDestination
    ) {
        composable(route = Screen.Lobby.route) {
            LobbyScreen(navigate = navigate)
        }
        playNavGraph(
            viewModel = viewModel,
            events = events,
            navigate = navigate,
        )
        composable(route = Screen.Settings.route) {
            SettingsScreen(navigate = navigate)
        }
    }
}

fun NavGraphBuilder.playNavGraph(
    viewModel: GameViewModel,
    events: (GameEvent) -> Unit,
    navigate: (NavRouts) -> Unit
) {
    val startDestination = Screen.CreateGame.route

    navigation(
        route = Screen.PlayNavGraph.route,
        startDestination = startDestination
    ) {
        composable(route = Screen.CreateGame.route) {
            val uiState by viewModel.uiState.collectAsState()

            CreateGameScreen(
                uiState = uiState,
                events = events,
                navigate = navigate
            )
        }
        composable(route = Screen.StartGameRound.route) {
            val uiState by viewModel.uiState.collectAsState()

            StartGameScreen(
                uiState = uiState,
                events = events,
                navigate = navigate
            )
        }
        composable(route = Screen.GameRound.route) {
            val uiState by viewModel.uiState.collectAsState()

            RunGameScreen(
                uiState = uiState,
                events = events,
                navigate = navigate
            )
        }
        composable(route = Screen.EndGameRound.route) {
            val uiState by viewModel.uiState.collectAsState()

            EndGameScreen(
                uiState = uiState,
                events = events,
                navigate = navigate
            )
        }
        composable(route = Screen.FinishGame.route) {
            val uiState by viewModel.uiState.collectAsState()

            FinishGameScreen(
                uiState = uiState,
                events = events,
                navigate = navigate
            )
        }
    }
}