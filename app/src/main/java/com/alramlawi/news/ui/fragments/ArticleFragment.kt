/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.alramlawi.news.R
import com.alramlawi.news.ui.NewsActivity
import com.alramlawi.news.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        viewModel = (activity as NewsActivity).newsViewModel
        val article = args.article
        webView.apply {
            webViewClient = AppWebViewClients(progress_circular)
            //webViewClient = WebViewClient() // to make sure open our app webView instead of phone web browser
            loadUrl(article.url)

        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            fab.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
            for(savedArticle in it){
                if(savedArticle.url == article.url){
                    fab.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_saved))
                }
            }
        })
    }

    inner class AppWebViewClients(private val progressBar: ProgressBar) : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.INVISIBLE
        }

        init {
            progressBar.visibility = View.VISIBLE
        }
    }
}