/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alramlawi.news.R
import com.alramlawi.news.db.ArticleDatabase
import com.alramlawi.news.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val repository = NewsRepository(ArticleDatabase(this))
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(application, repository)
        newsViewModel = ViewModelProvider(this, newsViewModelProviderFactory).get(NewsViewModel::class.java)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.breakingFragment, R.id.savedFragment, R.id.searchFragment)
        )
        setupActionBarWithNavController(findNavController(R.id.newsNavHostFragment), appBarConfiguration)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())


    }
}
