package com.rachitbhutani.openmovies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rachitbhutani.openmovies.MainViewModel
import com.rachitbhutani.openmovies.R
import com.rachitbhutani.openmovies.SortBy

@Composable
fun LayoutFilterGroup(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val isList by viewModel.showAsList.collectAsState()
    Row(
        modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .clickable {
                    viewModel.showAsList.value = true
                }
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_list),
            colorFilter = ColorFilter.tint(if (isList) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary),
            contentDescription = "List"
        )
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Image(
            modifier = modifier
                .clickable {
                    viewModel.showAsList.value = false
                }
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .size(20.dp),
            painter = painterResource(id = R.drawable.ic_grid),
            colorFilter = ColorFilter.tint(if (isList.not()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary),
            contentDescription = "Grid"
        )
    }
}

@Composable
fun SortFilterGroup(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val sortBy by viewModel.sortBy.collectAsState()
    Row(
        modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .clickable {
                    viewModel.sortBy.value = SortBy.Rating
                }
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .height(20.dp),
            text = "Rating",
            fontWeight = if (sortBy == SortBy.Rating) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(16.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Text(
            modifier = modifier
                .clickable {
                    viewModel.sortBy.value = SortBy.ReleaseYear
                }
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .height(20.dp),
            text = "Release Year",
            fontWeight = if (sortBy == SortBy.ReleaseYear) FontWeight.Bold else FontWeight.Normal
        )
    }
}
