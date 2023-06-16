package com.dicoding.callysta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.callysta.data.local.ProgressPreferences
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.repository.Repository

class HomepageViewModel(private val pref: ProgressPreferences, private val repository: Repository) : ViewModel() {
    fun getQuestionList() = repository.getQuestionList()

    fun getProgress(): LiveData<Progress> {
        return pref.getProgress().asLiveData()
    }

}