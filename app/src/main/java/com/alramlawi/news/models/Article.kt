/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alramlawi.news.models.Source
import java.io.Serializable

@Entity (tableName = "articles_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Long? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
):Serializable