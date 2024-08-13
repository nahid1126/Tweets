package com.nahid.tweets.di

import com.google.gson.GsonBuilder
import com.nahid.tweets.BuildConfig
import com.nahid.tweets.model.networks.ApiInterface
import com.nahid.tweets.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(interceptor)
            }
            connectTimeout(300, TimeUnit.SECONDS)
            readTimeout(300, TimeUnit.SECONDS)
            writeTimeout(300, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}