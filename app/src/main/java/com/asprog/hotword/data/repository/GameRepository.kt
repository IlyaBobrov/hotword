package com.asprog.hotword.data.repository

import com.asprog.hotword.data.interfaces.Repository

interface GameRepository : Repository {
    fun saveGame()
    suspend fun getMaxTimeRound(): Long
    suspend fun getMinTimeRound(): Long
    suspend fun getRoundCount(): Int
    suspend fun getShowTimer(): Boolean
    suspend fun updateMaxTimeRound(value: Long)
    suspend fun updateMinTimeRound(value: Long)
    suspend fun updateRoundCount(value: Int)
    suspend fun updateShowTimer(value: Boolean)
}