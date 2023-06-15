package com.dicoding.callysta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.callysta.core.di.Injection
import com.dicoding.callysta.core.repository.Repository
import com.dicoding.callysta.viewmodel.HomepageViewModel

class ViewModelFactory private constructor(
    private val repository: Repository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomepageViewModel::class.java)) {
            return HomepageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository()
                )
            }.also { instance = it }
    }
}
