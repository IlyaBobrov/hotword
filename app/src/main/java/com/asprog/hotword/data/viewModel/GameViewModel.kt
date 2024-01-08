package com.asprog.hotword.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.repository.GameRepository
import com.asprog.hotword.data.sample.Const.TAG
import com.asprog.hotword.data.sample.Const.second
import com.asprog.hotword.data.sample.KeyLocalSimpleDataBase.fourLetters
import com.asprog.hotword.data.sample.KeyLocalSimpleDataBase.threeLetters
import com.asprog.hotword.data.sample.KeyLocalSimpleDataBase.twoLetters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

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
                }
            }

            is GameEvent.StartGame -> {
                when (event) {
                    GameEvent.StartGame.Init -> {
                        startGameScreen()
                    }
                }
            }

            is GameEvent.EndGame -> {
                when (event) {
                    GameEvent.EndGame.Init -> {
                        _uiState.update {
                            it.copy(
                                currentScreenState = GameScreenState.END,
                                currentRound = (uiState.value.currentRound + 1)
                            )
                        }
                    }

                    is GameEvent.EndGame.LosePlayer -> {
                        val oldList = uiState.value.players.toMutableList()
                        val index = oldList.indexOf(event.player)
                        oldList.removeAt(index)
                        oldList.add(index, event.player.copy(count = (event.player.count + 1)))
                        _uiState.update { it.copy(players = oldList) }
                    }
                }
            }

            is GameEvent.FinishGame -> {
                when (event) {
                    GameEvent.FinishGame.Init -> {
                        _uiState.update {
                            it.copy(
                                currentScreenState = GameScreenState.FINISH,
                                boom = false
                            )
                        }
                    }
                }
            }

            is GameEvent.RunGame -> {
                when (event) {
                    GameEvent.RunGame.Init -> {
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
        //TODO исключать слова
        val l = (fourLetters + threeLetters + twoLetters).random()
        _uiState.update {
            it.copy(
                currentScreenState = GameScreenState.START,
                boom = false,
                currentWord = l
            )
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun runGameScreen() {
        _uiState.update { it.copy(currentScreenState = GameScreenState.RUN) }

        GlobalScope.launch(exceptionHandler) {
            var randomTime =
                (uiState.value.timeRound.first..uiState.value.timeRound.second).random()
            _uiState.update { it.copy(currentTimerInit = randomTime, currentTimer = randomTime) }
            val step = 50L
            while (randomTime > 0) {
                delay(step)
                randomTime -= step
                _uiState.update { it.copy(currentTimer = max(randomTime, 0L)) }
            }
            delay(step)
            _uiState.update { it.copy(boom = true) }
        }
    }

    private fun canRestartGame(): Boolean {
        return uiState.value.currentScreenState != GameScreenState.NOT_INIT &&
                uiState.value.currentScreenState != GameScreenState.FINISH
    }

    private fun setPersons(listPlayers: List<Player>) {
        _uiState.update { it.copy(players = listPlayers) }
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