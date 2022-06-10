package com.androiddevs.mvvmnewsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.androiddevs.mvvmnewsapp.domain.usecase.GetBreakingNewsUseCase

@Suppress("UNCHECKED_CAST")
class NewsViewModelProviderFactory(
    private val newsRepository: NewsRepositoryImpl,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository, getBreakingNewsUseCase) as T
    }
}