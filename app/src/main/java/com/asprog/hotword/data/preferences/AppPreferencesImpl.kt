package com.asprog.hotword.data.preferences

import android.content.SharedPreferences

interface AppPreferencesImpl {
    val sp: SharedPreferences


    val maxTimeRound: Long get() = _maxTimeRound
    private var _maxTimeRound: Long
        get() = sp.getLong("MAX_TIME_ROUND_KEY", 10000)
        set(value) = sp.edit().putLong("MAX_TIME_ROUND_KEY", value).apply()

    fun setMaxTimeRound(value: Long): Boolean {
        val isMaxTimeRound = _maxTimeRound != value
        _maxTimeRound = value
        return isMaxTimeRound
    }

    val minTimeRound: Long get() = _minTimeRound
    private var _minTimeRound: Long
        get() = sp.getLong("MIN_TIME_ROUND_KEY", 5000)
        set(value) = sp.edit().putLong("MIN_TIME_ROUND_KEY", value).apply()

    fun setMinTimeRound(value: Long): Boolean {
        val isMinTimeRound = _minTimeRound != value
        _minTimeRound = value
        return isMinTimeRound
    }

    val roundCount: Int get() = _roundCount
    private var _roundCount: Int
        get() = sp.getInt("ROUND_COUNT_KEY", 5)
        set(value) = sp.edit().putInt("ROUND_COUNT_KEY", value).apply()

    fun setRoundCount(value: Int): Boolean {
        val isRoundCount = _roundCount != value
        _roundCount = value
        return isRoundCount
    }

    val showTimer: Boolean get() = _showTimer
    private var _showTimer: Boolean
        get() = sp.getBoolean("SHOW_TIMER_KEY", true)
        set(value) = sp.edit().putBoolean("SHOW_TIMER_KEY", value).apply()

    fun setShowTimer(value: Boolean): Boolean {
        val isShowTimer = _showTimer != value
        _showTimer = value
        return isShowTimer
    }
}