package com.test.exchangeratetesttask.common

sealed class Resource<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = true,
    val message: String = "",
) {
    class Success<T>(data: T?, message: String = ""): Resource<T>(data = data, message = message)
    class Loading<T>(isLoading: Boolean = true): Resource<T>(isLoading = isLoading)
    class Failure<T>(message: String = ""): Resource<T>(isSuccessful = false, message = message)
}