package com.example.myapplication.data

import com.example.myapplication.model.NewsResponse

interface NewsRepository {
    suspend fun getNews(query: String): NewsResponse
}