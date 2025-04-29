package com.neotelemetrixgdscunand.kamekapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreferenceImpl @Inject constructor(
    private val dataStorePrefs: DataStore<Preferences>
): AuthPreference {

    override suspend fun saveToken(token: String) {
        dataStorePrefs.edit { prefs ->
            prefs[TOKEN] = token
        }
    }

    override fun getToken(): Flow<String> {
        return dataStorePrefs.data.map {
            it[TOKEN] ?: ""
        }
    }

    companion object{
        private val TOKEN = stringPreferencesKey("token")
    }

}