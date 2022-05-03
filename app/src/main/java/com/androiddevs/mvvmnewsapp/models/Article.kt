package com.androiddevs.mvvmnewsapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

//сохранение в локальную базу данных
@Entity(
    tableName = "articles"
)

data class Article(
    //автоматическая генерация ID
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded(prefix = "source")
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
): Serializable