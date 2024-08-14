package com.nahid.tweets.model.data

import com.google.gson.annotations.SerializedName

data class TweetList(
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("text")
    val text: String
)