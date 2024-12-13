package com.example.stuntkids.data.repository

import com.example.stuntkids.data.model.ArticleModel
import com.example.stuntkids.data.model.HistoryResponse
import com.example.stuntkids.data.service.ApiService
import com.example.stuntkids.data.service.ApiUtils
import com.example.stuntkids.data.service.NewsApiService
import com.example.stuntkids.model.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    private val apiService: ApiService = ApiUtils.getApiService()
    private val newsApiService: NewsApiService = ApiUtils.getNewsApiService()

    suspend fun insertPrediction(text: String) {
        return withContext(Dispatchers.IO) {
            apiService.insertPrediction(text)
        }
    }

    suspend fun getHistories(): HistoryResponse {
        return withContext(Dispatchers.IO) {
            val history = apiService.getHistories()
            val filtered = history.data.filter { it.history.result.split("-").size == 5 }
            return@withContext HistoryResponse(filtered)
        }
    }

    suspend fun getTopHeadlines(): List<Article> {
        return withContext(Dispatchers.IO) {
            // Memanggil API dengan parameter 'health' untuk kategori
            val response = newsApiService.getTopHeadlines("us", ApiUtils.NEWS_API_KEY, "health")

            // Memetakan hasil API ke dalam model Article
            response.articles.map {
                Article(
                    title = it.title ?: "",  // Pastikan fields ini ada di model Anda
                    content = it.description ?: "",
                    imageUrl = it.urlToImage ?: "",
                    url = it.url ?: ""
                )
            }
        }
    }
}