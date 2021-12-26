package com.mutualmobile.iswipe.data.states

sealed class ResponseState<out T> {
    object Empty : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
    class Success<T>(val data: T) : ResponseState<T>()
    class Failure(val errorMsg: String) : ResponseState<Nothing>()
}
