/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.api

import com.alramlawi.news.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        // lazy used to create one retrofit instance only
        val retrofit by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // OkHttpClient for debugging the response
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}