package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.repository.NewsPageSource
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.repository.SearchNewsSource
import com.androiddevs.mvvmnewsapp.utils.clearDescription
import com.androiddevs.mvvmnewsapp.utils.editDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun breakingNewsFlow(): Flow<PagingData<Article>> {
        return Pager(PagingConfig(pageSize = 5)) {
            NewsPageSource()
        }.flow
            .map { it -> it.filter { it.source.name != "Google News" } }
            .map { it ->
                it.map {
                    it.apply {
                        publishedAt = editDate(publishedAt)
                        description = clearDescription(description)
                    }
                }
            }
    }

    fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(pageSize = 5)
        ) {
            SearchNewsSource(query)
        }.flow
            .map { it -> it.filter { it.source.name != "Google News" } }
            .map { it ->
                it.map {
                    it.apply {
                        publishedAt = editDate(publishedAt)
                        description = clearDescription(description)
                    }
                }
            }
    }


    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews(): Flow<PagingData<Article>> {
        return Pager(PagingConfig(pageSize = 5)) {
            newsRepository.getSavedNews()
        }.flow.cachedIn(viewModelScope)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
