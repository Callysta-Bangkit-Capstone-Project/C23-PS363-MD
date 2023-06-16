package com.dicoding.callysta.viewmodel

import androidx.lifecycle.*
import com.dicoding.callysta.data.local.ProgressPreferences
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.utils.Response
import kotlinx.coroutines.launch

class LearnToWriteViewModel(private val pref: ProgressPreferences, private val repository: Repository) : ViewModel() {

    fun checkImage(request: ImageCheckRequest): LiveData<Response<Boolean>> {
        return repository.isImageValid(request)
    }

    fun updateProgress(data: Progress) {
        viewModelScope.launch {
            pref.updateProgress(data)
        }
    }

    fun getProgress(): LiveData<Progress> {
        return pref.getProgress().asLiveData()
    }


}