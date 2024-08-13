package com.nahid.tweets.di

import com.nahid.tweets.di.qualifier.TweetsQualifier
import com.nahid.tweets.model.repository.TweetsRepository
import com.nahid.tweets.model.repository.TweetsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryProvider {
    @Binds
    @TweetsQualifier
    abstract fun bindTweetsRepository(tweetsRepositoryImpl: TweetsRepositoryImpl): TweetsRepository
}