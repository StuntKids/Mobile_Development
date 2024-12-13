package com.example.stuntkids.data.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {
    const val BASE_URL = "http://34.101.254.198:3000/"
    const val NEWS_BASE_URL = "https://newsapi.org/v2/"
    const val NEWS_API_KEY = "ee64a24ab917440186bd0479b239d1f6"

    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun getOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    fun getRetrofitClient(
        baseUrl: String = BASE_URL,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiService {
        return getRetrofitClient().create(ApiService::class.java)
    }

    fun getNewsApiService(): NewsApiService {
        return getRetrofitClient(baseUrl = NEWS_BASE_URL).create(NewsApiService::class.java)
    }
}