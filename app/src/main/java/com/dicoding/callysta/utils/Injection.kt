package com.dicoding.callysta.utils

import com.dicoding.callysta.BuildConfig
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.data.remote.ApiConfig

object Injection {
    fun provideRepository(): Repository {
        val apiServiceAssets = ApiConfig.getApiService(BuildConfig.BASE_URL_ASSETS)
        val apiServiceCloud = ApiConfig.getApiService(BuildConfig.BASE_URL_CLOUD)
        return Repository(apiServiceAssets, apiServiceCloud)
    }
}