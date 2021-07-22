/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.repository

import com.alramlawi.news.api.RetrofitInstance
import com.alramlawi.news.db.ArticleDatabase
import com.alramlawi.news.models.Article

/*
This repository fo get data from Room database and api, but we can get api instance directly so,
we need not passing api parameter into repository, only database
 */

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upset(article: Article) = db.getArticleDao().upset(article)

    fun getArticles() = db.getArticleDao().getAllArticles()

    suspend fun delete(article: Article) = db.getArticleDao().deleteArticle(article)
}