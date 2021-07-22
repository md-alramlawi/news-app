/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.utils

/*
Resource class is recommended by Google, that used for network response
sealed means : only selected classes can extend it.

T refer to network response

 */
sealed class Resource<T>(val data: T? = null, val message: String? = null){

    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()

}