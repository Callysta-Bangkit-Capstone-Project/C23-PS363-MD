package com.dicoding.callysta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.callysta.data.local.ProgressPreferences
import com.dicoding.callysta.model.AudioCheckResponse
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.model.Progress
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.utils.Response
import kotlinx.coroutines.launch
import java.io.File

class LearnToReadViewModel(private val pref: ProgressPreferences, private val repository: Repository) : ViewModel() {

    fun checkAudio(file: File): LiveData<Response<AudioCheckResponse>> {
        return repository.isAudioValid(file)
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