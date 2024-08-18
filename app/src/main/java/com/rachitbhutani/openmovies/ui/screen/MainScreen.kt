package com.rachitbhutani.openmovies.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rachitbhutani.openmovies.MainViewModel
import com.rachitbhutani.openmovies.ui.components.LayoutFilterGroup
import com.rachitbhutani.openmovies.ui.components.MovieBucket
import com.rachitbhutani.openmovies.ui.components.SearchBar
import com.rachitbhutani.openmovies.ui.components.SortFilterGroup
import com.rachitbhutani.openmovies.utils.PageState


@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    var query by remember { mutableStateOf("") }
    val movies = remember { viewModel.moviesFlow }

    val isList by viewModel.showAsList.collectAsState()
    val sortPref by viewModel.sortPref.collectAsState()

    val pageStatus by viewModel.pageState.collectAsState()

    LaunchedEffect(query) {
        if (query.isNotEmpty()) viewModel.getMovies(query.trim())
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        //Search
        SearchBar {
            if (query == it.trim()) return@SearchBar
            query = it
        }
        Spacer(modifier = Modifier.height(8.dp))

        //Filters
        Row {
            LayoutFilterGroup(modifier = Modifier.padding(vertical = 4.dp), isList) {
                viewModel.showAsList.value = it
            }
            Spacer(modifier = Modifier.width(8.dp))
            SortFilterGroup(modifier = Modifier.padding(vertical = 4.dp), sortPref) {
                viewModel.sortPref.value = it
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        //Content
        if (pageStatus == PageState.Error) {
            Text(
                "Something's not right",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
            )
        } else MovieBucket(movies = movies)

        //Loader
        if (pageStatus == PageState.Loading) {
            LinearProgressIndicator(
                Modifier
                    .width(36.dp)
                    .align(Alignment.CenterHorizontally))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}