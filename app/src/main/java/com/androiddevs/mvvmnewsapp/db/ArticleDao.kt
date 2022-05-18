package com.androiddevs.mvvmnewsapp.db


import androidx.paging.PagingSource
import androidx.room.*
import com.androiddevs.mvvmnewsapp.models.Article


//интерфейс базы данных
@Dao
interface ArticleDao {


    //Добавление записей, при совпадении записи обновить
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article:Article): Long

    //Получение записей из базы данных
    @Query("SELECT * FROM articles ")
    fun getAllArticles():PagingSource<Int,Article>

    //Удаление записей из базы данных
    @Delete
    suspend fun deleteArticle(article: Article)


}