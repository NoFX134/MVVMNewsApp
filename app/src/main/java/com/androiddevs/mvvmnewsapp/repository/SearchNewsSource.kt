package com.androiddevs.mvvmnewsapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.utils.Constants
import retrofit2.HttpException

class SearchNewsSource(private val query: String): PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
        anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
}

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val pageNumber: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(Constants.MAX_PAGE_SIZE)
        val response =
            RetrofitInstance.api.searchNews(query, pageNumber, pageSize)
        return if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).articles
            val nextKey = if (articles.size < pageSize) null else pageNumber + 1
            val prevKey = if (pageNumber == 1) null else pageNumber - 1
            LoadResult.Page(articles, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))

        }
    }
}
