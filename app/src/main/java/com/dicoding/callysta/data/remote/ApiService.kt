package com.dicoding.callysta.data.remote

import com.dicoding.callysta.model.QuestionResponse
import retrofit2.http.GET

interface ApiService {

    @GET("soal.json")
    suspend fun getQuestionList(): QuestionResponse


}