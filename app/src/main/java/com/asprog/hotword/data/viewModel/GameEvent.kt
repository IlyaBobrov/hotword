package com.asprog.hotword.data.viewModel

import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.interfaces.Event

sealed interface GameEvent : Event {
    sealed interface CreateScreen : GameEvent {
        data object Init : CreateScreen
        data class SetTimeRound(val minTime: Int, val maxTime: Int) : CreateScreen
        data class SetPersons(val newListPlayers: List<Player>) : CreateScreen
        data class SetMaxRounds(val maxRounds: Int) : CreateScreen
    }

    sealed interface StartGame : GameEvent {
        data object Init : StartGame
    }

    sealed interface RunGame : GameEvent {
        data object Init : RunGame
    }

    sealed interface EndGame : GameEvent {
        data object Init : EndGame
        data class LosePlayer(val player: Player) : EndGame
    }

    sealed interface FinishGame : GameEvent {
        data object Init : FinishGame
    }
}