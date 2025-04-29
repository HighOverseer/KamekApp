package com.neotelemetrixgdscunand.kakaoxpert.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ):DataStore<Preferences> {
        return context.dataStore
    }
}