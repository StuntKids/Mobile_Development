package com.example.stuntkids.data.service

import com.example.stuntkids.data.model.HistoryResponse
import com.example.stuntkids.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-Type: text/plain")
    @POST("predict")
    suspend fun insertPrediction(@Body text: String)

    @GET("predict/histories")
    suspend fun getHistories(): HistoryResponse
}