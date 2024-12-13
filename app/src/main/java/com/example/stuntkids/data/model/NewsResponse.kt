package com.example.stuntkids.data.model

import com.example.stuntkids.model.Article

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleModel>
)