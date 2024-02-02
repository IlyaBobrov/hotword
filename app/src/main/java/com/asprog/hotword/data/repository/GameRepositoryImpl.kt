package com.asprog.hotword.data.repository

import com.asprog.hotword.data.preferences.PreferencesStorage
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val storage: PreferencesStorage
) : GameRepository {
    override fun saveGame() {
        storage.getAppPreferences()
    }

    override suspend fun getMaxTimeRound(): Long {
        return storage.getAppPreferences().maxTimeRound
    }

    override suspend fun getMinTimeRound(): Long {
        return storage.getAppPreferences().minTimeRound
    }

    override suspend fun getRoundCount(): Int {
        return storage.getAppPreferences().roundCount
    }

    override suspend fun getShowTimer(): Boolean {
        return storage.getAppPreferences().showTimer
    }

    override suspend fun updateMaxTimeRound(value: Long) {
        storage.getAppPreferences().setMaxTimeRound(value)
    }

    override suspend fun updateMinTimeRound(value: Long) {
        storage.getAppPreferences().setMinTimeRound(value)
    }

    override suspend fun updateRoundCount(value: Int) {
        storage.getAppPreferences().setRoundCount(value)
    }

    override suspend fun updateShowTimer(value: Boolean) {
        storage.getAppPreferences().setShowTimer(value)
    }
}