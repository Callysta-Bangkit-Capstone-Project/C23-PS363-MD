package com.dicoding.callysta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.callysta.utils.Injection
import com.dicoding.callysta.repository.Repository

class ViewModelFactory private constructor(
    private val repository: Repository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomepageViewModel::class.java) -> HomepageViewModel(repository) as T
            modelClass.isAssignableFrom(LearnToWriteViewModel::class.java) -> LearnToWriteViewModel(repository) as T
            modelClass.isAssignableFrom(LearnToReadViewModel::class.java) -> LearnToReadViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
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
