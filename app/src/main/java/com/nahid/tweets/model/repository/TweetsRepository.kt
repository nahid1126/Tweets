package com.nahid.tweets.model.repository

import com.nahid.tweets.model.data.TweetList
import com.nahid.tweets.model.networks.NetworkResponse
import kotlinx.coroutines.flow.SharedFlow

interface TweetsRepository {
    suspend fun requestForTweetsCategory()
    val tweetsCategoryResponse: SharedFlow<NetworkResponse<List<String>>>

    suspend fun requestForTweets(category: String)
    val tweetsResponse: SharedFlow<NetworkResponse<List<TweetList>>>
}