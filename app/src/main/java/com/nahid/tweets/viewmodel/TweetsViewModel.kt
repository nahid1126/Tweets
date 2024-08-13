package com.nahid.tweets.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nahid.tweets.di.qualifier.TweetsQualifier
import com.nahid.tweets.model.repository.TweetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(
    @TweetsQualifier private val tweetsRepository: TweetsRepository
) : ViewModel() {
    var message = MutableSharedFlow<String>()

    val tweetsCategory = tweetsRepository.tweetsCategoryResponse

    fun reqForTweetsCategory() {
        viewModelScope.launch {
            tweetsRepository.requestForTweetsCategory()
        }
    }

    val tweets = tweetsRepository.tweetsResponse

    fun reqForTweets(category: String) {
        viewModelScope.launch {
            tweetsRepository.requestForTweets(category)
        }
    }
}