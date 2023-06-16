package com.dicoding.callysta.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.utils.Response

class LearnToWriteViewModel(private val repository: Repository) : ViewModel() {

    fun checkImage(request: ImageCheckRequest): LiveData<Response<Boolean>> {
        return repository.isImageValid(request)
    }


}