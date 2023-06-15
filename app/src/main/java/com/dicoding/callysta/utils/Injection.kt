package com.dicoding.callysta.utils

import com.dicoding.callysta.BuildConfig
import com.dicoding.callysta.repository.Repository
import com.dicoding.callysta.data.remote.ApiConfig

object Injection {
    fun provideRepository(): Repository {
        val apiServiceAssets = ApiConfig.getApiServiceAssets()
        val apiServiceCloud = ApiConfig.getApiServiceCloud()
        return Repository(apiServiceAssets, apiServiceCloud)
    }
}