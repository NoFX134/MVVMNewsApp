package com.androiddevs.mvvmnewsapp.data.models

import com.androiddevs.mvvmnewsapp.data.models.Article

//data class нашего запроса
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)