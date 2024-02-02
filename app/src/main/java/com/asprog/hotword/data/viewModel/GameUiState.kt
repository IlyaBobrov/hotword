package com.asprog.hotword.data.viewModel

import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.sample.Const


enum class GameScreenState {
    NOT_INIT, CREATE, START, RUN, END, FINISH, PAUSE
}
data class GameUiState(
    val currentScreenState: GameScreenState = GameScreenState.NOT_INIT,

    val players: List<Player> = listOf(),

    val currentRound: Int = 1,
    val currentWord: String = "ЗОР",
    val listUsed: List<String> = listOf(),
    val currentTimerInit: Long = 10000L,
    val currentTimer: Long = 10000L,
    val boom: Boolean = false,

    val errorMessage: String = "",

    //settings
    val maxRounds: Int = 5,
    val minTimeRound: Long = 10 * Const.second,
    val maxTimeRound: Long = 30 * Const.second,
    val showTimer: Boolean = false,

    val messageToast: String? = null,
)