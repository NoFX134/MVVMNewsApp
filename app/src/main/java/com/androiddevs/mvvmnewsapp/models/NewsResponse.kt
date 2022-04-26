package com.androiddevs.mvvmnewsapp.models
//data class нашего запроса
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)