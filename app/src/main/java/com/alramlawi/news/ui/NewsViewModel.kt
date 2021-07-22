/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alramlawi.news.MyApplication
import com.alramlawi.news.models.Article
import com.alramlawi.news.models.NewsResponse
import com.alramlawi.news.repository.NewsRepository
import com.alramlawi.news.utils.Constants.Companion.COUNTRY_CODE
import com.alramlawi.news.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

/*
Because we do not use a default constructor, we have to create NewsViewModelProviderFactory that extends ViewModelProvider.Factory
and pass repository as a parameter so we can use it to create our customized ViewModel
 */

class NewsViewModel(
    val app: Application,
    val repository: NewsRepository
) : AndroidViewModel(app) {

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null


    val searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery:String? = null
    var oldSearchQuery:String? = null

    init {
        getBreakingNews(COUNTRY_CODE)
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }



    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleSearchResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPage++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeSearchNewsCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNewsLiveData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.searchNews(searchQuery, searchNewsPage)
                Log.d("search page: $searchNewsPage", response.body()?.articles?.toList()?.size.toString())
                searchNewsLiveData.postValue(handleSearchResponse(response))
            } else {
                searchNewsLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNewsLiveData.postValue(Resource.Error("Network Failure"))
                else -> searchNewsLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        newsLiveData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getBreakingNews(countryCode, breakingNewsPage)
                Log.d("breaking page: $searchNewsPage", response.body()?.articles?.toList()?.size.toString())
                newsLiveData.postValue(handleBreakingNewsResponse(response))
            } else {
                newsLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> newsLiveData.postValue(Resource.Error("Network Failure"))
                else -> newsLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upset(article)
    }

    fun getSavedArticles() = repository.getArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

}