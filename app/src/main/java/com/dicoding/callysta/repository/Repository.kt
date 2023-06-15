package com.dicoding.callysta.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.callysta.data.remote.ApiServiceAssets
import com.dicoding.callysta.data.remote.ApiServiceCloud
import com.dicoding.callysta.model.ImageCheckRequest
import com.dicoding.callysta.model.QuestionResponse
import com.dicoding.callysta.utils.Response

class Repository(
    private val apiServiceAssets: ApiServiceAssets,
    private val apiServiceCloud: ApiServiceCloud
) {

    fun getQuestionList(): LiveData<Response<QuestionResponse>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServiceAssets
                .getQuestionList()
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

    fun isImageValid(request: ImageCheckRequest): LiveData<Response<Boolean>> = liveData {
        emit(Response.Loading)
        try {
            val response = apiServiceCloud.checkImage(request)
            emit(Response.Success(response))
        } catch (e: Exception) {
            emit(Response.Error(e.message.toString()))
        }
    }

}