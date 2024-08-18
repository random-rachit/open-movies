package com.rachitbhutani.openmovies

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rachitbhutani.openmovies.data.MovieRepository
import com.rachitbhutani.openmovies.data.remote.MovieItemResponse
import com.rachitbhutani.openmovies.network.NetworkResponse
import com.rachitbhutani.openmovies.utils.PageState
import com.rachitbhutani.openmovies.utils.SortPref
import com.rachitbhutani.openmovies.utils.handleApi
import com.rachitbhutani.openmovies.utils.mapWithRatings
import com.rachitbhutani.openmovies.utils.sortItems
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
    val sortPref = MutableStateFlow(SortPref.Rating)

    private var currentQuery = "Lord"
    private var currentPage = 0

    var pageState = MutableStateFlow(PageState.Idle)
        private set

    private var searchJob: Job? = null

    fun getMovies(query: String = currentQuery) {
        val isReset = query != currentQuery
        if (isReset) {
            searchJob?.cancel()
            pageState.value = PageState.Loading
            currentPage = 1
        }
        searchJob = viewModelScope.launch {
            delay(1000)
            if (isReset.not()) {
                currentPage++
            } else {
                moviesFlow.clear()
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
                        response.body?.search?.mapWithRatings()?.sortItems(sortPref.value).orEmpty()
                    )
                }
            }
        }
    }

}