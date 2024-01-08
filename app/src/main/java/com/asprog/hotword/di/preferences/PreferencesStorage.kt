package com.asprog.hotword.di.preferences

interface PreferencesStorage {
    fun getAppPreferences(): AppPreferences
}