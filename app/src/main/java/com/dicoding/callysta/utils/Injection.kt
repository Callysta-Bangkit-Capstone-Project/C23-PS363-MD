package com.dicoding.callysta.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dicoding.callysta.BuildConfig
import com.dicoding.callysta.data.local.ProgressPreferences
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.data.remote.ApiConfig

object Injection {
    fun provideProgressPref(dataStore: DataStore<Preferences>): ProgressPreferences {
        return ProgressPreferences.getInstance(dataStore)
    }

    fun provideRepository(): Repository {
        val apiServiceAssets = ApiConfig.getApiServiceAssets()
        val apiServiceCloud = ApiConfig.getApiServiceCloud()
        return Repository(apiServiceAssets, apiServiceCloud)
    }
}