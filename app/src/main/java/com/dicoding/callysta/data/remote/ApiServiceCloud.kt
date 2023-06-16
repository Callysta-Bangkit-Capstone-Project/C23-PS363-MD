package com.dicoding.callysta.data.remote

import com.dicoding.callysta.model.AudioCheckResponse
import com.dicoding.callysta.model.ImageCheckRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiServiceCloud {

    @POST("ml/inputGambar")
    suspend fun checkImage(
        @Body request: ImageCheckRequest
    ): Boolean
    @Multipart
    @POST("ml/speech-to-text/")
    suspend fun checkSound(
        @Part file: MultipartBody.Part,
    ): AudioCheckResponse

}