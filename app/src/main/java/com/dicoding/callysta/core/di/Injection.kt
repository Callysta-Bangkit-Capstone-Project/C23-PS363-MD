package com.dicoding.callysta.core.di

import android.content.Context
import com.dicoding.callysta.BuildConfig
import com.dicoding.callysta.core.repository.Repository
import com.dicoding.callysta.core.source.network.ApiConfig

object Injection {
    fun provideRepository(): Repository {
        val apiServiceAssets = ApiConfig.getApiService(BuildConfig.BASE_URL_ASSETS)
        val apiServiceCloud = ApiConfig.getApiService(BuildConfig.BASE_URL_CLOUD)
        return Repository(apiServiceAssets, apiServiceCloud)
    }
}