package com.rachitbhutani.openmovies.data

import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieService: MovieService) {

    suspend fun getMovies(query: String, pageNo: Int) =
        movieService.getMovies(query = query, page = pageNo)
}