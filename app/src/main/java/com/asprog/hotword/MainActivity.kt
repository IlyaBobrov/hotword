package com.asprog.hotword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.asprog.hotword.data.entity.Screen
import com.asprog.hotword.navigation.game.createGame.CreateGameScreen
import com.asprog.hotword.navigation.game.createGame.createGameNavigationAction
import com.asprog.hotword.navigation.lobby.LobbyScreen
import com.asprog.hotword.navigation.lobby.lobbyNavigationAction
import com.asprog.hotword.ui.theme.HotPotatoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            navController = rememberNavController()

            AppContainer(navController)
        }
    }
}

@Composable
fun AppContainer(navController:NavHostController) {
    HotPotatoTheme {

        NavHost(
            navController = navController,
            startDestination = Screen.LobbyNavGraph.route,
            route = Screen.App.route,
        ){
            lobbyNavGraph(navController)
        }
    }
}

fun NavGraphBuilder.lobbyNavGraph(navController: NavHostController) {
    val startDestination = Screen.Lobby.route

    navigation(
        route = Screen.LobbyNavGraph.route,
        startDestination = startDestination
    ) {
        composable(route = Screen.Lobby.route) {
            LobbyScreen {navigator ->
                lobbyNavigationAction(navigator = navigator, navController = navController)
            }
        }
        playNavGraph(navController = navController)

        composable(route = Screen.Settings.route) {

        }
    }
}

fun NavGraphBuilder.playNavGraph(navController: NavHostController) {
    val startDestination = Screen.CreateGame.route

    navigation(
        route = Screen.PlayNavGraph.route,
        startDestination = startDestination
    ) {
        composable(route = Screen.CreateGame.route) {
            CreateGameScreen { navigator ->
                createGameNavigationAction(navigator = navigator, navController = navController)
            }
        }
        composable(route = Screen.StartGameRound.route) {

        }
        composable(route = Screen.GameRound.route) {

        }
        composable(route = Screen.EndGameRound.route) {

        }
        composable(route = Screen.FinishGame.route) {

        }
    }
}
