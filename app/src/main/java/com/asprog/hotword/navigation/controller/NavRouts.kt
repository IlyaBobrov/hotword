package com.asprog.hotword.navigation.controller

import com.asprog.hotword.interfaces.Navigation

sealed interface NavRouts : Navigation {
    sealed interface FromSettings : NavRouts {
        data object ToCreateGame : FromSettings
    }
    sealed interface FromLobby : NavRouts {

        data object ToCreateGame : FromLobby
    }
    sealed interface FromCreateGame : NavRouts {

        data object ToLobby : FromCreateGame
        data object ToStartGame : FromCreateGame
        data object ToSettings : FromCreateGame
    }
    sealed interface FromStartGame : NavRouts {
        data object ToCreateGame : FromStartGame
        data object ToRunGame : FromStartGame
        data object ToFinishGame : FromStartGame
    }
    sealed interface FromRunGame : NavRouts {
        data object ToEndGame : FromRunGame
        data object ToFinishGame : FromRunGame
    }
    sealed interface FromEndGame : NavRouts {
        data object ToStartGame : FromEndGame
        data object ToFinishGame : FromEndGame
    }

    sealed interface FromFinishGame : NavRouts {
        data object ToLobbyGame : FromFinishGame
    }
}
