package com.nahid.tweets.views.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nahid.tweets.model.networks.NetworkResponse
import com.nahid.tweets.viewmodel.TweetsViewModel

private const val TAG = "DetailsScreen"

@Composable
fun DetailsScreen() {
    val tweetsViewModel: TweetsViewModel = hiltViewModel<TweetsViewModel>()
    LaunchedEffect(Unit) {
        tweetsViewModel.reqForTweets("android")
    }
    val tweets by tweetsViewModel.tweets.collectAsState(initial = NetworkResponse.Loading())
    when (tweets) {
        is NetworkResponse.Empty -> {

        }

        is NetworkResponse.Error -> {
            Text(text = (tweets as NetworkResponse.Error).message.toString() ?: "Error")
        }

        is NetworkResponse.Loading -> {
            Text(
                text = "Loading..."
            )
        }

        is NetworkResponse.Success -> {
            val tweetsList = (tweets as NetworkResponse.Success).data
            LazyColumn {
                tweetsList?.let { list ->
                    items(list.size) {
                        TweetsListItem(tweet = list[it].text)
                    }
                }
            }
        }
    }
}

@Composable
fun TweetsListItem(tweet: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        border = BorderStroke(1.dp, Color(0xFFCCCCCC)),
        content = {
            Text(
                text = tweet,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}