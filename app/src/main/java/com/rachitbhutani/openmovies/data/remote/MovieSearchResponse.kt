package com.rachitbhutani.openmovies.data.remote

import android.util.Log
import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("Search")
    val search: List<MovieItemResponse>?,
    @SerializedName("Response")
    val response: String?,
    val totalResults: String?,
)

data class MovieItemResponse(
    @SerializedName("Title")
    val title: String?,
    @SerializedName("Year")
    val year: String?,
    @SerializedName("Poster")
    val poster: String?,
    val imdbID: String?,
) {
    var rating: Int = 0
}