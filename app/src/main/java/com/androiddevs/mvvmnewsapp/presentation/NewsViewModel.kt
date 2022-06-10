package com.androiddevs.mvvmnewsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.androiddevs.mvvmnewsapp.data.local.dataSource.NewsPageSource
import com.androiddevs.mvvmnewsapp.data.local.dataSource.SearchNewsSource
import com.androiddevs.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.androiddevs.mvvmnewsapp.data.models.Article
import com.androiddevs.mvvmnewsapp.domain.usecase.GetBreakingNewsUseCase
import com.androiddevs.mvvmnewsapp.utils.Constants.rkn
import com.androiddevs.mvvmnewsapp.utils.clearDescription
import com.androiddevs.mvvmnewsapp.utils.editDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class NewsViewModel(
    private val newsRepository: NewsRepositoryImpl,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase
) : ViewModel() {

    private var query = ""

    fun setQuery(query: String) {
        this.query = query
    }

    val breakingNewsFlow = getBreakingNewsUseCase.execute().cachedIn(viewModelScope)

//    val searchNews  =  Pager(
//            PagingConfig(pageSize = 5)
//        ) {
//            SearchNewsSource(query)
//        }.flow
//        .map { it -> it.filter { rkn.indexOf(it.source.name)<0 } }
//        .map { it ->
//            it.map {
//                it.apply {
//                    publishedAt = editDate(publishedAt)
//                    description = clearDescription(description)
//                    source.name = source.name.lowercase()
//                }
//            }
//        }.cachedIn(viewModelScope)


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
