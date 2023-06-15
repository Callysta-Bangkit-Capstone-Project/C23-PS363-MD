package com.dicoding.callysta.data.remote

import com.dicoding.callysta.model.QuestionResponse
import retrofit2.http.GET

interface ApiServiceAssets {

    @GET("soal.json")
    suspend fun getQuestionList(): QuestionResponse

}