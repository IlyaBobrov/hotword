package com.asprog.hotword.navigation.game.data.repository

import com.asprog.hotword.di.preferences.PreferencesStorage
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val storage: PreferencesStorage
) : GameRepository {
    override fun saveGame() {
        storage.getAppPreferences()
    }
}