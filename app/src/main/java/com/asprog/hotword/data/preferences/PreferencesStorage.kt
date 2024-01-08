package com.asprog.hotword.data.preferences

interface PreferencesStorage {
    fun getAppPreferences(): AppPreferences
}