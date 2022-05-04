package com.androiddevs.mvvmnewsapp.models


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @NonNull
    val author: String?,
    @NonNull
    val content: String?,
    @NonNull
    val description: String?,
    @NonNull
    val publishedAt: String?,
    val source: Source,
    @NonNull
    val title: String?,
    @NonNull
    val url: String?,
    @NonNull
    val urlToImage: String?
) : Serializable