package com.rachitbhutani.openmovies.utils

import com.rachitbhutani.openmovies.data.remote.MovieItemResponse
import com.rachitbhutani.openmovies.network.ErrorResponse
import com.rachitbhutani.openmovies.network.NetworkResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T: Any> handleApi(execute: suspend () -> Response<T>): NetworkResponse<T> {
    return try {
        val response = execute.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResponse.Success(body)
        } else {
            NetworkResponse.Error(
                ErrorResponse(
                    response.errorBody().toString(),
                    response.code()
                )
            )
        }
    } catch (e: HttpException) {
        e.printStackTrace()
        NetworkResponse.Error(ErrorResponse(e.message(), e.code()))
    } catch (e: IOException) {
        NetworkResponse.Error(ErrorResponse(message = NO_INTERNET_MESSAGE))
    } catch (e: Exception) {
        NetworkResponse.Error(ErrorResponse(message = e.message))
    }
}

fun List<MovieItemResponse>.mapWithRatings() = map {
    it.rating = (Math.random() * 10).toInt().coerceIn(1,10)
    it
}



fun List<MovieItemResponse>.sortItems(sort: SortPref): List<MovieItemResponse> {
    return this.sortedByDescending {
        when (sort) {
            SortPref.Rating -> it.rating
            SortPref.ReleaseYear -> it.year?.toInt() ?: 0
        }
    }
}

enum class PageState {
    Idle, Loading, Error, Refresh;
}

enum class SortPref {
    Rating, ReleaseYear
}