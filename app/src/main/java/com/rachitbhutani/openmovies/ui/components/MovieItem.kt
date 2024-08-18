package com.rachitbhutani.openmovies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rachitbhutani.openmovies.data.local.MovieUiData

@Composable
fun MovieListItem(modifier: Modifier = Modifier, data: MovieUiData) {
    Row(
        modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Thumbnail(
            modifier = Modifier.aspectRatio(1f),
            image = data.thumbnail,
            caption = "${data.ratings} ⭐️"
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.title.orEmpty(),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = data.releaseYear.orEmpty(), color = MaterialTheme.colorScheme.secondary, maxLines = 3)
        }
    }

}

@Composable
fun MovieGridItem(modifier: Modifier = Modifier, data: MovieUiData) {
    Column(modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Thumbnail(
            image = data.thumbnail,
            caption = "${data.ratings} ⭐️"
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = data.title.orEmpty(),
            style = TextStyle(fontWeight = FontWeight.Medium),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Thumbnail(modifier: Modifier = Modifier, image: String?, caption: String?) {
    Box(modifier.clip(RoundedCornerShape(10.dp))) {
        AsyncImage(
            model = image,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
            contentDescription = caption,
            contentScale = ContentScale.Crop
        )

        Text(
            text = caption.orEmpty(),
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .background(Brush.verticalGradient(0.2f to Color.Transparent, 1f to Color.Black))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview
@Composable
fun MovieItemPrev() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MovieListItem(
            data = MovieUiData("Inside Out", "An animated film", "", "15 Aug, 2024", 4)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(Modifier.height(200.dp)) {
            MovieGridItem(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                data = MovieUiData("Inside Out", "An animated film", "", "15 Aug, 2024", 4)
            )
            MovieGridItem(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                data = MovieUiData("Inside Out", "An animated film", "", "15 Aug, 2024", 4)
            )
        }
    }
}