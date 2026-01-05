package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.NewsRepository
import com.example.myapplication.repository.NewsNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Provides
    @Singleton
    fun provideNewsRepository(
        client: OkHttpClient
    ): NewsRepository = NewsNetworkRepository(
        client = client,
        apiKey = BuildConfig.API_KEY
    )
}