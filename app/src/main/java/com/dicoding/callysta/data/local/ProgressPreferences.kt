package com.dicoding.callysta.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.dicoding.callysta.model.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgressPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun updateProgress(data: Progress) {
        dataStore.edit { preferences ->
            preferences[LEVEL_READ] = data.levelRead
            preferences[SUBLEVEL_READ] = data.subLevelRead
            preferences[LEVEL_WRITE] = data.levelWrite
            preferences[SUBLEVEL_WRITE] = data.subLevelWrite
        }
    }

    fun getProgress(): Flow<Progress> {
        return dataStore.data.map { preferences ->
            Progress(
                preferences[LEVEL_READ] ?: 1,
                preferences[SUBLEVEL_READ] ?: 1,
                preferences[LEVEL_WRITE] ?: 1,
                preferences[SUBLEVEL_WRITE] ?: 1
            )
        }
    }

    companion object {
        private val LEVEL_READ = intPreferencesKey("level_read")
        private val SUBLEVEL_READ = intPreferencesKey("sublevel_read")
        private val LEVEL_WRITE = intPreferencesKey("level_write")
        private val SUBLEVEL_WRITE = intPreferencesKey("sublevel_write")

        @Volatile
        private var INSTANCE: ProgressPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): ProgressPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ProgressPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}