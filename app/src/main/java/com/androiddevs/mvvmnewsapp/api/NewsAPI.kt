package com.androiddevs.mvvmnewsapp.api


import androidx.annotation.IntRange
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.utils.Constants.API_KEY
import com.androiddevs.mvvmnewsapp.utils.Constants.DEFAULT_PAGE_SIZE
import com.androiddevs.mvvmnewsapp.utils.Constants.MAX_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс Retrofit, формирует запросы к нашему API
 * @GET добавляет часть адреса к запросу
 * @Query suspend getBreakingNews формирует дополнительную часть запроса:
 * страна, страница и ключ API
 * @Query suspend searchNews формирует дополнительную часть запроса:
 * ключевое слово для поиска, страница, язык статьи и ключ API
 **/


interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "ru",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") @IntRange(
            from = 1,
            to = MAX_PAGE_SIZE.toLong()
        ) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("apiKey") apiKey: String = API_KEY

    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") @IntRange(
            from = 1,
            to = MAX_PAGE_SIZE.toLong()
        ) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("language") language: String = "ru",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}