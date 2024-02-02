package com.asprog.hotword.data.viewModel

import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.interfaces.Event

sealed interface GameEvent : Event {
    sealed interface CreateScreen : GameEvent {
        data object Init : CreateScreen
        data class SetPersons(val newListPlayers: List<Player>) : CreateScreen
        data object ClearGame : CreateScreen
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

    sealed interface Settings : GameEvent {
        data class RoundCountUpdate(val value: Int) : Settings
        data class MinTimeRoundUpdate(val value: Long) : Settings
        data class MaxTimeRoundUpdate(val value: Long) : Settings
        data class ShowTimerUpdate(val value: Boolean) : Settings
    }
}