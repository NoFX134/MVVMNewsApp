package com.androiddevs.mvvmnewsapp.domain.repository

import androidx.paging.PagingSource
import com.androiddevs.mvvmnewsapp.data.models.Article


interface NewsRepository {

    suspend fun upsert(article: Article): Long
    fun getSavedNews(): PagingSource<Int, Article>
    suspend fun deleteArticle(article: Article)
    fun breakingNews(): PagingSource<Int, Article>
    fun searchNews(query: String): PagingSource<Int, Article>

}