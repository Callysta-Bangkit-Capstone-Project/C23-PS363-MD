package com.dicoding.callysta.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.callysta.data.local.ProgressPreferences
import com.dicoding.callysta.utils.Injection
import com.dicoding.callysta.repository.Repository

class ViewModelFactory private constructor(
    private val pref: ProgressPreferences,
    private val repository: Repository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomepageViewModel::class.java) -> HomepageViewModel(pref, repository) as T
            modelClass.isAssignableFrom(LearnToWriteViewModel::class.java) -> LearnToWriteViewModel(pref, repository) as T
            modelClass.isAssignableFrom(LearnToReadViewModel::class.java) -> LearnToReadViewModel(pref, repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(dataStore: DataStore<Preferences>): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideProgressPref(dataStore),
                    Injection.provideRepository()
                )
            }.also { instance = it }
    }
}
