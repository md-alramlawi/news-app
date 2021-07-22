/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.models

data class NewsResponse(
    val articles: MutableList<Article>,  // MutableList and not List to enable adding to articles list
    val status: String,
    val totalResults: Int
)