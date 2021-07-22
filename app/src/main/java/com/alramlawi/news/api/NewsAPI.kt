/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.api

import com.alramlawi.news.models.NewsResponse
import com.alramlawi.news.utils.Constants.Companion.API_KEY
import com.alramlawi.news.utils.Constants.Companion.COUNTRY_CODE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = COUNTRY_CODE, // default value = us
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}