package com.dicoding.callysta.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.callysta.data.remote.ApiService
import com.dicoding.callysta.model.QuestionResponse
import com.dicoding.callysta.utils.Response

class Repository(
    private val apiServiceAssets: ApiService,
    private val apiServiceCloud: ApiService
) {

    fun getQuestionList(): LiveData<Response<QuestionResponse>> =
        liveData {
            emit(Response.Loading)
            try {
                val response = apiServiceAssets
                    .getQuestionList()
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(e.message.toString()))
            }
        }

}