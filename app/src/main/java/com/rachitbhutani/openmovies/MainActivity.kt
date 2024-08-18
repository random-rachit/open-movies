package com.rachitbhutani.openmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rachitbhutani.openmovies.ui.components.LayoutFilterGroup
import com.rachitbhutani.openmovies.ui.components.MovieBucket
import com.rachitbhutani.openmovies.ui.components.SearchBar
import com.rachitbhutani.openmovies.ui.components.SortFilterGroup
import com.rachitbhutani.openmovies.ui.theme.OpenMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    var query by remember { mutableStateOf("") }
    val movies = remember { viewModel.moviesFlow }

    val pageStatus by viewModel.pageState.collectAsState()

    LaunchedEffect(query) {
        if (query != "") viewModel.getMovies(query.trim())
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        SearchBar {
            if (query == it.trim()) return@SearchBar
            query = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            LayoutFilterGroup(modifier = Modifier.padding(vertical = 4.dp))
            Spacer(modifier = Modifier.width(8.dp))
            SortFilterGroup(modifier = Modifier.padding(vertical = 4.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (pageStatus == PageState.Error || movies.isEmpty()) {
            Text(
                "Something's not right",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize().padding(top = 24.dp)
            )
        } else MovieBucket(movies = movies)
        if (pageStatus == PageState.Loading) {
            LinearProgressIndicator(Modifier.width(24.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
