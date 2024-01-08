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
    val currentWord: String = "",
    val currentTimerInit: Long = 10000L,
    val currentTimer: Long = 10000L,
    val boom: Boolean = false,

    val maxRounds: Int = 5,

    val timeRound: Pair<Long, Long> = Pair(5 * Const.second, 20 * Const.second),

    val errorMessage: String = ""
)