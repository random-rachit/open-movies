package com.rachitbhutani.openmovies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rachitbhutani.openmovies.MainViewModel
import com.rachitbhutani.openmovies.data.local.MovieUiData
import com.rachitbhutani.openmovies.data.remote.MovieItemResponse
import com.rachitbhutani.openmovies.utils.PageState
import com.rachitbhutani.openmovies.utils.sortItems

@Composable
fun MovieBucket(
    modifier: Modifier = Modifier,
    movies: SnapshotStateList<MovieItemResponse>
) {
    val viewModel: MainViewModel = hiltViewModel()
    val isList by viewModel.showAsList.collectAsState()
    val spanSize = if (isList) 2 else 1
    val listState = rememberLazyGridState()
    val pageState by viewModel.pageState.collectAsState()
    val sortBy by viewModel.sortBy.collectAsState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            pageState == PageState.Idle || pageState == PageState.Refresh
                    && (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (listState.layoutInfo.totalItemsCount - 6)
        }
    }

    LaunchedEffect(key1 = pageState) {
        if (pageState == PageState.Refresh) {
            listState.scrollToItem(0)
        }
    }

    LaunchedEffect(shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            viewModel.getMovies()
        }
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ) {
        items(movies.sortItems(sortBy), span = { GridItemSpan(spanSize) }, key = { movie ->
            movie.imdbID ?: Math.random().toString()
        }) {
            val data = it.toMovieUiData()

            if (isList) MovieListItem(modifier = Modifier.padding(4.dp), data = data)
            else MovieGridItem(modifier = Modifier.padding(4.dp), data = data)

            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            if (viewModel.pageState.value == PageState.Error) {
                Text(
                    "Something's Empty",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 24.dp)
                )
            }
        }
    }
}

private fun MovieItemResponse?.toMovieUiData(): MovieUiData {
    return MovieUiData(this?.title, this?.poster, this?.year, this?.rating)
}
