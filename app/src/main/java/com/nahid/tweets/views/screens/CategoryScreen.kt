package com.nahid.tweets.views.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nahid.tweets.model.networks.NetworkResponse
import com.nahid.tweets.viewmodel.TweetsViewModel

@Composable
fun CategoryScreen(
    viewModel: TweetsViewModel = hiltViewModel()
) {
    // Start requesting categories when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.reqForTweetsCategory()
    }

    // Observe the tweetsCategory state from the ViewModel
    val tweetsCategoryResponse by viewModel.tweetsCategory.collectAsState(initial = NetworkResponse.Loading())

    when (tweetsCategoryResponse) {
        is NetworkResponse.Loading -> {
            // Show a loading UI or placeholder if needed
            Text(text = "Loading...")
        }

        is NetworkResponse.Success -> {
            val categories = (tweetsCategoryResponse as NetworkResponse.Success<List<String>>).data

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                items(categories!!.size) { index ->
                    CategoryItem(category = categories[index])
                }
            }
        }

        is NetworkResponse.Error -> {
            // Handle error state
            Text(text = (tweetsCategoryResponse as NetworkResponse.Error).message.toString() ?: "Error")
        }

        is NetworkResponse.Empty -> {

        }
    }
}


@Composable
fun CategoryItem(category: String) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(160.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFEEEEEE)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = category,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp, 20.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}