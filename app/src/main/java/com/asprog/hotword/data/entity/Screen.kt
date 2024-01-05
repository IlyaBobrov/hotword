package com.asprog.hotword.data.entity


sealed class Screen(
    val route: String,
    val title: String,
) {
    companion object {
        val TITLE_UNDEFINED: String = "Без навзвания"
    }

    data object App : Screen("app", TITLE_UNDEFINED)


    data object LobbyNavGraph : Screen("lobbyNavGraph", "Игра")

    data object Lobby : Screen("lobby", "Главная")
    data object Settings : Screen("settings", "Settings")


    data object PlayNavGraph : Screen("playNavGraph", "Игра")

    data object CreateGame : Screen("createGame", "CreateGame")
    data object StartGameRound : Screen("startGameRound", "StartGame")
    data object GameRound : Screen("gameRound", "Game")
    data object EndGameRound : Screen("endGameRound", "EndGame")
    data object FinishGame : Screen("finishGame", "FinishGame")
}