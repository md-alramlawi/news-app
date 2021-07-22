/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alramlawi.news.repository.NewsRepository

class NewsViewModelProviderFactory(
    val app: Application,
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}