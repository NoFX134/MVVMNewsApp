package com.androiddevs.mvvmnewsapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


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
    val description: String?,
    val publishedAt: String?,
    @Embedded(prefix = "source")
    val source: Source,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable {
    fun clearDescription(): String? {
        return description?.let { Regex("""[</a-z>]""").replace(it, "") }
    }
}