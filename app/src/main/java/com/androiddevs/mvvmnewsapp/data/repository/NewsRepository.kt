package com.androiddevs.mvvmnewsapp.data.repository

import com.androiddevs.mvvmnewsapp.data.local.dataSource.NewsPageSource
import com.androiddevs.mvvmnewsapp.data.local.dataSource.SearchNewsSource
import com.androiddevs.mvvmnewsapp.data.remote.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.data.models.Article
import com.androiddevs.mvvmnewsapp.domain.repository.NewsRepository

class NewsRepositoryImpl(private val db: ArticleDatabase): NewsRepository {

    override suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    override fun getSavedNews() = db.getArticleDao().getAllArticles()

    override suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    override fun breakingNews() = NewsPageSource()

    override fun searchNews(query: String) = SearchNewsSource(query)
}

