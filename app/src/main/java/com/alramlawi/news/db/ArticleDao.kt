/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alramlawi.news.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upset(article: Article): Long

    @Query("SELECT * FROM articles_table")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}