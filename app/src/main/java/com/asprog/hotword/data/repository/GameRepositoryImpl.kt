package com.asprog.hotword.data.repository

import com.asprog.hotword.data.preferences.PreferencesStorage
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val storage: PreferencesStorage
) : GameRepository {
    override fun saveGame() {
        storage.getAppPreferences()
    }
}