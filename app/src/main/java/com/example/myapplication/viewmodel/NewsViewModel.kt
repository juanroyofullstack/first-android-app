package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.NewsRepository
import com.example.myapplication.model.NewsIntent
import com.example.myapplication.model.NewsItem
import com.example.myapplication.model.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.SearchNews -> {
                fetchNews(intent.query)
            }

            is NewsIntent.LoadInitialNews -> loadInitialNews()
            is NewsIntent.SelectedNewsDetail -> selectedNewsDetail(intent.article)
        }
    }

    private fun selectedNewsDetail(article: NewsItem) {
        _uiState.update { it.copy(selectedArticleDetail = article) }
    }

    private fun unselectNewsDetail() {
        _uiState.update { it.copy(selectedArticleDetail = null) }
    }

    private fun loadInitialNews() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = runCatching { newsRepository.getNews("Google") }

            _uiState.update {
                result.fold(
                    onSuccess = { response ->
                        it.copy(
                            isLoading = false,
                            articles = response.articles
                        )
                    },
                    onFailure = { error ->
                        it.copy(
                            isLoading = false,
                            error = "Fallo al cargar noticias $error"
                        )
                    }
                )
            }
        }
    }

    private fun fetchNews(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = runCatching { newsRepository.getNews(query) }

            _uiState.update {
                result.fold(
                    onSuccess = { response ->
                        it.copy(
                            isLoading = false,
                            articles = response.articles
                        )
                    },
                    onFailure = { error ->
                        it.copy(
                            isLoading = false,
                            error = "Fallo al cargar noticias $error"
                        )
                    }
                )
            }
        }
    }
}