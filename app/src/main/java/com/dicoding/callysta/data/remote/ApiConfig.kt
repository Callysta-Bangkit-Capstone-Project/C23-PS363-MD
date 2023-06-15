package com.dicoding.callysta.data.remote

import com.dicoding.callysta.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    fun getApiServiceAssets(): ApiServiceAssets {
        val retrofit = getRetrofit(BuildConfig.BASE_URL_ASSETS)
        return retrofit.create(ApiServiceAssets::class.java)
    }

    fun getApiServiceCloud(): ApiServiceCloud {
        val retrofit = getRetrofit(BuildConfig.BASE_URL_CLOUD)
        return retrofit.create(ApiServiceCloud::class.java)
    }

    private fun getRetrofit(baseUrl: String): Retrofit {

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }
}