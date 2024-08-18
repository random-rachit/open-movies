package com.rachitbhutani.openmovies.network

sealed class NetworkResponse<out T> {
    data class Success<out T>(val body: T?): NetworkResponse<T>()
    data class Error(val error: ErrorResponse): NetworkResponse<Nothing>()
}

data class ErrorResponse(
    override val message: String?,
    val code: Int? = null
): Throwable(message)