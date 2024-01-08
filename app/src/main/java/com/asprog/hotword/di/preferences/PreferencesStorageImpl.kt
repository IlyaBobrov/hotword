package com.asprog.hotword.di.preferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesStorageImpl
@Inject constructor(
    private val context: Context
) : PreferencesStorage {
    private lateinit var appPreferences: AppPreferences

    override fun getAppPreferences(): AppPreferences {
        return if (this::appPreferences.isInitialized) appPreferences
        else {
            appPreferences = AppPreferences(
                EncryptedSharedPreferences.create(
                    context,
                    "sharedPrefsFile",
                    MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            )
            appPreferences
        }
    }
}