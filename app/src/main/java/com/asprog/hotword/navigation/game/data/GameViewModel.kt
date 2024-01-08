package com.asprog.hotword.navigation.game.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.sample.Const.TAG
import com.asprog.hotword.data.sample.Const.second
import com.asprog.hotword.interfaces.Event
import com.asprog.hotword.navigation.game.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface GameEvent : Event {
    sealed interface CreateScreen : GameEvent {
        data object Init: CreateScreen
        data class SetTimeRound(val minTime: Int, val maxTime: Int) : CreateScreen
        data class SetPersons(val newListPlayers: List<Player>) : CreateScreen
        data class SetMaxRounds(val maxRounds: Int) : CreateScreen

        data object  OpenStartScreen: CreateScreen
    }
    sealed interface StartGame: GameEvent {
        data object Init: StartGame
        data object RunRound: StartGame
    }
}

@HiltViewModel
class GameViewModel @Inject constructor(
    val repository: GameRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { Log.e(TAG, it) }
        throwable.printStackTrace()
    }

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onEvent(event: GameEvent) {
        Log.d(TAG, "event: " + event.javaClass.name.toString())
        when (event) {
            is GameEvent.CreateScreen -> {
                when (event) {
                    GameEvent.CreateScreen.Init -> {
                        goCreateGameScreen()
                    }
                    is GameEvent.CreateScreen.SetMaxRounds -> {
                        setMaxRounds(event.maxRounds)
                    }
                    is GameEvent.CreateScreen.SetPersons -> {
                        setPersons(event.newListPlayers)
                    }
                    is GameEvent.CreateScreen.SetTimeRound -> {
                        setTimeRound(Pair(event.minTime, event.maxTime))
                    }
                    GameEvent.CreateScreen.OpenStartScreen -> {
                        startGameScreen()
                    }
                }
            }

            is GameEvent.StartGame -> {
                when (event) {
                    GameEvent.StartGame.Init -> {
                        //TODO
                    }
                    GameEvent.StartGame.RunRound -> {
                        runGameScreen()
                    }
                }
            }
        }
    }

    private fun goCreateGameScreen() {
        if (canRestartGame()) {
            _uiState.update {
                it.copy(currentScreenState = GameScreenState.CREATE)
            }
        } else {
            _uiState.value = GameUiState(currentScreenState = GameScreenState.CREATE)
        }
    }

    private fun startGameScreen() {
        //TODO check params
        _uiState.update { it.copy(currentScreenState = GameScreenState.START) }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun runGameScreen() {
        _uiState.update { it.copy(currentScreenState = GameScreenState.RUN) }
        GlobalScope.launch(exceptionHandler) {

        }
    }


    private fun canRestartGame(): Boolean {
        return uiState.value.currentScreenState != GameScreenState.NOT_INIT
    }

    private fun setPersons(listPlayers: List<Player>) {
        _uiState.update { it.copy(players = listPlayers) }
        Log.d(TAG, "!1: $listPlayers")
        Log.d(TAG, "!2: " + uiState.value.players.toString())
    }

    private fun setTimeRound(timeRound: Pair<Int, Int>) {
        _uiState.update {
            it.copy(
                timeRound = Pair(
                    timeRound.first * second,
                    timeRound.second * second
                )
            )
        }
    }

    private fun setMaxRounds(rounds: Int) {
        _uiState.update { it.copy(maxRounds = rounds) }
    }
}