package com.androiddevs.mvvmnewsapp.domain.usecase

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.androiddevs.mvvmnewsapp.data.local.dataSource.NewsPageSource
import com.androiddevs.mvvmnewsapp.data.models.Article
import com.androiddevs.mvvmnewsapp.domain.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utils.Constants
import com.androiddevs.mvvmnewsapp.utils.clearDescription
import com.androiddevs.mvvmnewsapp.utils.editDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBreakingNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<PagingData<Article>> {
        return Pager(PagingConfig(pageSize = 5)) {
            NewsPageSource()
        }.flow
            .map { it -> it.filter { Constants.rkn.indexOf(it.source.name)<0 } }
            .map { it ->
                it.map {
                    it.apply {
                        publishedAt = editDate(publishedAt)
                        description = clearDescription(description)
                        source.name = source.name.lowercase()
                    }
                }
            }
    }
}

