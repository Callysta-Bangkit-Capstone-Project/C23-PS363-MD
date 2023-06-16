package com.dicoding.callysta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.callysta.model.AudioCheckResponse
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.utils.Response
import java.io.File

class LearnToReadViewModel(private val repository: Repository) : ViewModel() {

    fun checkAudio(file: File): LiveData<Response<AudioCheckResponse>> {
        return repository.isAudioValid(file)
    }
}