package com.asprog.hotword.di.preferences


import android.content.SharedPreferences

class AppPreferences(
    override val sp: SharedPreferences
) : AppPreferencesImpl