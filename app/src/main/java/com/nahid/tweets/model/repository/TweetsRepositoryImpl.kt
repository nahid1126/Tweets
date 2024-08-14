package com.nahid.tweets.model.repository

import android.util.Log
import com.nahid.tweets.model.data.TweetList
import com.nahid.tweets.model.networks.ApiInterface
import com.nahid.tweets.model.networks.NetworkResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

private const val TAG = "TweetsRepositoryImpl"

class TweetsRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) :
    TweetsRepository {
    private val _tweetsCategoryResponse = MutableSharedFlow<NetworkResponse<List<String>>>()
    private val _tweetsResponse = MutableSharedFlow<NetworkResponse<List<TweetList>>>()

    override val tweetsCategoryResponse: SharedFlow<NetworkResponse<List<String>>>
        get() = _tweetsCategoryResponse.asSharedFlow()

    override val tweetsResponse: SharedFlow<NetworkResponse<List<TweetList>>>
        get() = _tweetsResponse.asSharedFlow()

    override suspend fun requestForTweetsCategory() {
        _tweetsCategoryResponse.emit(NetworkResponse.Loading())
        try {
            val response = apiInterface.getTweetsCategory()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    Log.d(TAG, "requestForTweetsCategory: ")
                    _tweetsCategoryResponse.emit(
                        NetworkResponse.Success(
                            response.body()!!.distinct()
                        )
                    )
                } else {
                    _tweetsCategoryResponse.emit(NetworkResponse.Error("Something went wrong"))
                }
            } else {
                _tweetsCategoryResponse.emit(NetworkResponse.Error("Something went wrong"))
            }
        } catch (e: Exception) {
            _tweetsCategoryResponse.emit(NetworkResponse.Error(e.message.toString()))
        }
    }


    override suspend fun requestForTweets(category: String) {
        _tweetsResponse.emit(NetworkResponse.Loading())
        try {
            val response = apiInterface.getTweets("tweets[?(@.category==\"$category\")]")
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    _tweetsResponse.emit(NetworkResponse.Success(body))
                } else {
                    _tweetsResponse.emit(NetworkResponse.Error("Something went wrong"))
                }
            } else {
                _tweetsResponse.emit(NetworkResponse.Error("Something went wrong"))
            }
        } catch (e: Exception) {
            _tweetsResponse.emit(NetworkResponse.Error(e.message.toString()))
        }
    }
}