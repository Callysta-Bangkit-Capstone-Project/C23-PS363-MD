package com.dicoding.callysta.data.remote

import com.dicoding.callysta.model.ImageCheckRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceCloud {

    @POST("ml/inputGambar")
    suspend fun checkImage(@Body request: ImageCheckRequest): Boolean

}