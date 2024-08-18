package com.rachitbhutani.openmovies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rachitbhutani.openmovies.R
import com.rachitbhutani.openmovies.utils.SortPref

@Composable
fun LayoutFilterGroup(
    modifier: Modifier = Modifier,
    isList: Boolean,
    updateFilter: (Boolean) -> Unit
) {
    Row(
        modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .clickable {
                    updateFilter.invoke(true)
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
                    updateFilter.invoke(false)
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
fun SortFilterGroup(modifier: Modifier = Modifier, sortPref: SortPref, onSortChange: (SortPref) -> Unit) {
    Row(
        modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .clickable {
                    onSortChange.invoke(SortPref.Rating)
                }
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .height(20.dp),
            text = stringResource(R.string.rating),
            fontWeight = if (sortPref == SortPref.Rating) FontWeight.Bold else FontWeight.Normal
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
                    onSortChange(SortPref.ReleaseYear)
                }
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .height(20.dp),
            text = stringResource(R.string.release_year),
            fontWeight = if (sortPref == SortPref.ReleaseYear) FontWeight.Bold else FontWeight.Normal
        )
    }
}
