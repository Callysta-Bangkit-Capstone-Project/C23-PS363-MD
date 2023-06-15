package com.dicoding.callysta.core.source.network

import com.dicoding.callysta.core.response.QuestionResponse
import retrofit2.http.GET

interface ApiService {

    @GET("soal.json")
    suspend fun getQuestionList(): QuestionResponse


}