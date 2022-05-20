package com.androiddevs.mvvmnewsapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.androiddevs.mvvmnewsapp.utils.Constants
import java.io.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Entity(
    tableName = "articles",
    indices = [Index(
        value = ["title"],
        unique = true
    )]
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    var description: String?,
    var publishedAt: String?,
    @Embedded(prefix = "source")
    val source: Source,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable
