package com.asprog.hotword.navigation.controller

import androidx.navigation.NavHostController
import com.asprog.hotword.data.entity.Screen

fun globalNavigationGraph(
    navRouts: NavRouts,
    navController: NavHostController
) {

    when(navRouts) {
        is NavRouts.FromSettings -> {
            when(navRouts) {
                NavRouts.FromSettings.ToCreateGame -> {
                    navController.navigate(Screen.CreateGame.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
            }
        }
        is NavRouts.FromLobby -> {
            when(navRouts) {
                NavRouts.FromLobby.ToCreateGame -> {
                    navController.navigate(Screen.CreateGame.route)
                }
            }
        }
        is NavRouts.FromCreateGame -> {
            when(navRouts) {
                NavRouts.FromCreateGame.ToLobby -> {
                    navController.navigate(Screen.Lobby.route) {
                        popUpTo(Screen.LobbyNavGraph.route)
                    }
                }
                NavRouts.FromCreateGame.ToSettings -> {
                    navController.navigate(Screen.Settings.route)
                }
                NavRouts.FromCreateGame.ToStartGame -> {
                    navController.navigate(Screen.StartGameRound.route)
                }
            }
        }
        is NavRouts.FromStartGame -> {
            when(navRouts) {
                NavRouts.FromStartGame.ToCreateGame -> {
                    navController.navigate(Screen.CreateGame.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
                NavRouts.FromStartGame.ToRunGame -> {
                    navController.navigate(Screen.GameRound.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
                NavRouts.FromStartGame.ToFinishGame -> {
                    navController.navigate(Screen.FinishGame.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
            }
        }
        is NavRouts.FromRunGame -> {
            when(navRouts) {
                NavRouts.FromRunGame.ToEndGame -> {
                    navController.navigate(Screen.EndGameRound.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
                NavRouts.FromRunGame.ToFinishGame -> {
                    navController.navigate(Screen.FinishGame.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
            }
        }
        is NavRouts.FromEndGame -> {
            when(navRouts) {
                NavRouts.FromEndGame.ToStartGame -> {
                    navController.navigate(Screen.StartGameRound.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
                NavRouts.FromEndGame.ToFinishGame -> {
                    navController.navigate(Screen.FinishGame.route) {
                        popUpTo(Screen.PlayNavGraph.route)
                    }
                }
            }
        }
        is NavRouts.FromFinishGame -> {
            when(navRouts) {
                NavRouts.FromFinishGame.ToLobbyGame -> {
                    navController.navigate(Screen.Lobby.route) {
                        popUpTo(Screen.LobbyNavGraph.route)
                    }
                }
            }
        }
    }
}