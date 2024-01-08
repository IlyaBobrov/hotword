package com.asprog.hotword.di


import android.app.Application
import android.content.Context
import com.asprog.hotword.data.preferences.PreferencesStorage
import com.asprog.hotword.data.preferences.PreferencesStorageImpl
import com.asprog.hotword.data.repository.GameRepository
import com.asprog.hotword.data.repository.GameRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providePreferencesStorage(context: Context): PreferencesStorage {
        return PreferencesStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideGameRepository(storage: PreferencesStorage): GameRepository {
        return GameRepositoryImpl(storage)
    }
}