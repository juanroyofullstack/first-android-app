package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String,
    val url: String,
    val country: String
)

@Serializable
data class NewsItem(
    val id: String,
    val title: String,
    val content: String,
    val description: String,
    val url: String,
    val image: String,
    val publishedAt: String,
    val lang: String,
    val source: Source,
    val author: String? = null,
    val date: String? = null,
)

@Serializable
data class NewsResponse(
    val totalArticles: Int,
    val articles: List<NewsItem>
)

@Serializable
data class NewsUiState(
    val articles: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedArticleDetail: NewsItem? = null
)

sealed interface NewsIntent {
    data class SearchNews(val query: String) : NewsIntent
    data class LoadInitialNews(val dummy: Unit = Unit) : NewsIntent
    data class SelectedNewsDetail(val article: NewsItem) : NewsIntent
}