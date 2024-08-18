package com.rachitbhutani.openmovies

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachitbhutani.openmovies.data.MovieRepository
import com.rachitbhutani.openmovies.data.remote.MovieItemResponse
import com.rachitbhutani.openmovies.network.NetworkResponse
import com.rachitbhutani.openmovies.utils.handleApi
import com.rachitbhutani.openmovies.utils.mapWithRatings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    val moviesFlow = mutableStateListOf<MovieItemResponse>()

    val showAsList = MutableStateFlow(true)
    val sortBy = MutableStateFlow(SortBy.Rating)

    private var currentQuery = "Lord"
    private var currentPage = 0
    var pageState = MutableStateFlow(PageState.Idle)
        private set

    private var searchJob: Job? = null

    fun getMovies(query: String = currentQuery) {
        val isReset = query != currentQuery
        if (isReset) {
            searchJob?.cancel()
            pageState.value = PageState.Idle
            currentPage = 1
        }
        searchJob = viewModelScope.launch {
            delay(500)
            if (isReset.not()) {
                currentPage++
            } else {
                currentQuery = query
            }
            pageState.value = PageState.Loading
            when (val response = handleApi { repository.getMovies(currentQuery, currentPage) }) {
                is NetworkResponse.Error -> {
                    moviesFlow.clear()
                    pageState.value = PageState.Error
                }

                is NetworkResponse.Success -> {
                    if (isReset || currentPage == 1) {
                        moviesFlow.clear()
                        pageState.value = PageState.Refresh
                    } else pageState.value = PageState.Idle
                    moviesFlow.addAll(
                        response.body?.search?.mapWithRatings()?.sortItems(sortBy.value).orEmpty()
                    )
                }
            }
        }
    }

}

fun List<MovieItemResponse>.sortItems(sort: SortBy): List<MovieItemResponse> {
    return this.sortedByDescending {
        when (sort) {
            SortBy.Rating -> it.rating
            SortBy.ReleaseYear -> it.year?.toInt() ?: 0
        }
    }
}

enum class PageState {
    Idle, Loading, Error, Refresh;
}

enum class SortBy {
    Rating, ReleaseYear
}