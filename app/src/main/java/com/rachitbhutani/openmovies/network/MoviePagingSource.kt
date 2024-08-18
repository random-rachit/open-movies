package com.rachitbhutani.openmovies.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rachitbhutani.openmovies.data.MovieService
import com.rachitbhutani.openmovies.data.remote.MovieItemResponse
import com.rachitbhutani.openmovies.utils.handleApi
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val newsApiService: MovieService,
) : PagingSource<Int, MovieItemResponse>() {

    var query: String = "Lord"

    override fun getRefreshKey(state: PagingState<Int, MovieItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemResponse> {
        return try {
            val page = params.key ?: 1
            when (val response = handleApi { newsApiService.getMovies(page = page, query = query) }) {
                is NetworkResponse.Error -> throw response.error
                is NetworkResponse.Success -> LoadResult.Page(
                    data = response.body?.search?.map {
                        it.rating = (Math.random() * 10).toInt()
                        it
                    }.orEmpty(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = page + 1,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}