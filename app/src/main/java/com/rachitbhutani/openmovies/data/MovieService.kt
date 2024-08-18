package com.rachitbhutani.openmovies.data

import com.rachitbhutani.openmovies.data.remote.MovieSearchResponse
import com.rachitbhutani.openmovies.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("type") type: String = "movie",
        @Query("s") query: String,
        @Query("page") page: Int
    ): Response<MovieSearchResponse>

}