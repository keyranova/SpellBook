package com.keyranovack.spellbook.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class UserSettings(val favorites: Set<String>)

private const val SETTINGS_NAME = "settings"

val Context.dataStore by preferencesDataStore(
    name = SETTINGS_NAME
)

class AppDataStore(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {
    val userSettingsFlow: Flow<UserSettings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val favorites = preferences[FAVORITES] ?: emptySet()
            UserSettings(favorites)
        }

    suspend fun updateFavorites(favorites: Set<String>) {
        dataStore.edit { preferences ->
            preferences[FAVORITES] = favorites
        }
    }

    companion object {
        val FAVORITES = stringSetPreferencesKey("favorites")
    }
}