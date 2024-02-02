package com.asprog.hotword.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.repository.GameRepository
import com.asprog.hotword.data.sample.Const.TAG
import com.asprog.hotword.data.sample.Const.second
import com.asprog.hotword.data.sample.KeyLocalSimpleDataBase.fourLetters
import com.asprog.hotword.data.sample.KeyLocalSimpleDataBase.threeLetters
import com.asprog.hotword.data.sample.KeyLocalSimpleDataBase.twoLetters
import com.asprog.tools_kit.extensions.corutines.cancelForce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var maxTimeRoundJob: Job? = null
    private var minTimeRoundJob: Job? = null
    private var roundCountJob: Job? = null
    private var showTimerJob: Job? = null

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

                    is GameEvent.CreateScreen.SetPersons -> {
                        setPersons(event.newListPlayers)
                    }

                    GameEvent.CreateScreen.ClearGame -> {
                        goCreateGameScreen(true)
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

            is GameEvent.Settings -> {
                when (event) {
                    is GameEvent.Settings.MaxTimeRoundUpdate -> {
                        setMaxTimeRound(event.value)
                    }

                    is GameEvent.Settings.MinTimeRoundUpdate -> {
                        setMinTimeRound(event.value)
                    }

                    is GameEvent.Settings.RoundCountUpdate -> {
                        setRoundCount(event.value)
                    }

                    is GameEvent.Settings.ShowTimerUpdate -> {
                        setShowTimer(event.value)
                    }
                }
            }
        }
    }

    init {
        recallSettings()
    }

    private fun recallSettings() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    maxTimeRound = repository.getMaxTimeRound(),
                    minTimeRound = repository.getMinTimeRound(),
                    maxRounds = repository.getRoundCount(),
                    showTimer = repository.getShowTimer(),
                )
            }
        }
    }

    private fun goCreateGameScreen(force: Boolean = false) {
        if (!force && canRestartGame()) {
            _uiState.update {
                it.copy(currentScreenState = GameScreenState.CREATE)
            }
        } else {
            _uiState.value = GameUiState(currentScreenState = GameScreenState.CREATE)
            recallSettings()
        }
    }

    private fun startGameScreen() {
        val allList = (fourLetters + threeLetters + twoLetters).toMutableList()
        val usedList = uiState.value.listUsed.toMutableList()
        allList.removeAll(usedList)
        val selectedWord = allList.random()
        usedList.add(selectedWord)

        _uiState.update {
            it.copy(
                currentScreenState = GameScreenState.START,
                boom = false,
                currentWord = selectedWord,
                listUsed = usedList
            )
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun runGameScreen() {
        _uiState.update { it.copy(currentScreenState = GameScreenState.RUN) }

        GlobalScope.launch(exceptionHandler) {
            var randomTime =
                (uiState.value.minTimeRound..uiState.value.maxTimeRound).random()
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

    private fun setMaxTimeRound(value: Long) {
        _uiState.update { it.copy(maxTimeRound = value) }
        maxTimeRoundJob.cancelForce()
        maxTimeRoundJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(3 * second)
                repository.updateMaxTimeRound(value)
                showSaveMessage()
            }
        }
    }

    private fun setMinTimeRound(value: Long) {
        _uiState.update { it.copy(minTimeRound = value) }
        minTimeRoundJob.cancelForce()
        minTimeRoundJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(3 * second)
                repository.updateMinTimeRound(value)
                showSaveMessage()
            }
        }
    }

    private fun setRoundCount(value: Int) {
        _uiState.update { it.copy(maxRounds = value) }
        roundCountJob.cancelForce()
        roundCountJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(3 * second)
                repository.updateRoundCount(value)
                showSaveMessage()
            }
        }
    }

    private fun setShowTimer(value: Boolean) {
        _uiState.update { it.copy(showTimer = value) }
        showTimerJob.cancelForce()
        showTimerJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                delay(3 * second)
                repository.updateShowTimer(value)
                showSaveMessage()
            }
        }
    }

    private fun showSaveMessage() {
        _uiState.update { it.copy(messageToast = "Сохранено!") }
    }

    private fun hideMessage() {
        _uiState.update { it.copy(messageToast = null) }
    }

}