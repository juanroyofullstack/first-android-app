package com.example.myapplication.repository

import com.example.myapplication.data.NewsRepository
import com.example.myapplication.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class NewsNetworkRepository(
    private val client: OkHttpClient,
    private val apiKey: String
) : NewsRepository {
    private val json = Json { ignoreUnknownKeys = true }

    private val baseUrl = "https://gnews.io/api/v4/search".toHttpUrl()

    override suspend fun getNews(query: String): NewsResponse =
        withContext(Dispatchers.IO) {
            val url = baseUrl.newBuilder()
                .addQueryParameter("q", query)
                .addQueryParameter("lang", "en")
                .addQueryParameter("max", "5")
                .addQueryParameter("apikey", apiKey)
                .build()

            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) error("HTTP error ${response.code}")
                val body = response.body?.string().orEmpty()
                json.decodeFromString<NewsResponse>(body)
            }
        }
}