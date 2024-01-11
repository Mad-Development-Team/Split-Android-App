package com.madteam.split.utils.network

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception, val errorMessage: String? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}