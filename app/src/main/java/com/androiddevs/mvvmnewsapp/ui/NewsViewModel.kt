package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.androiddevs.mvvmnewsapp.api.RetrofitInstance.api
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsPageSource
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utils.Constants.DEFAULT_PAGE_SIZE
import com.androiddevs.mvvmnewsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val breakingNewsPage = 1
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val searchNewsPage = 1
    val breakingNewsFlow: Flow<PagingData<Article>> = Pager(
        PagingConfig(pageSize = 5),
    ){NewsPageSource()}.flow.cachedIn(viewModelScope)

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
