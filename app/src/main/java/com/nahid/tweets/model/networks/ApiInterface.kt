package com.nahid.tweets.model.networks

import com.nahid.tweets.model.data.TweetList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ApiInterface {
    @GET("b/66b9d97be41b4d34e41f33b7?meta=false")
    suspend fun getTweets(
        @Header("X-JSON-Path") category: String
    ): Response<List<TweetList>>

    @GET("b/66b9d97be41b4d34e41f33b7?meta=false")
    @Headers("X-JSON-Path : tweets..category")
    suspend fun getTweetsCategory(): Response<List<String>>

}