package com.asprog.hotword.navigation.game.data

import com.asprog.hotword.data.entity.Player
import com.asprog.hotword.data.sample.Const


enum class GameScreenState {
    NOT_INIT, CREATE, START, RUN, END, FINISH, PAUSE
}
data class GameUiState(
    val currentScreenState: GameScreenState = GameScreenState.NOT_INIT,


    val players: List<Player> = listOf(),

    val currentRound: Int = 0,
    val currentWord: String = "",
    val currentTimer: Long = 0L,

    val maxRounds: Int = 10,

    val timeRound: Pair <Long, Long> = Pair(10 * Const.second, 25 * Const.second),

    val errorMessage: String = ""
)