package com.example.myapplication.ui.screens

import android.util.Log
import androidx.activity.result.launch
import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.model.NewsIntent
import com.example.myapplication.model.NewsResponse
import com.example.myapplication.model.NewsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

private val json = Json { ignoreUnknownKeys = true }

private val logging = HttpLoggingInterceptor().apply {
    if(BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val apiKey = BuildConfig.API_KEY

private suspend fun fetchLatestNews(query: String = "Google"): NewsResponse = withContext(Dispatchers.IO) {
    Log.d("NewsFetch", "Llamando a gnews con OkHttp")
    val request = Request.Builder()
        .url("https://gnews.io/api/v4/search?q=$query&lang=en&max=5&apikey=$apiKey")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) error("HTTP error ${response.code}: ${response.message}")
        val body = response.body?.string().orEmpty()
        json.decodeFromString<NewsResponse>(body)
    }
}

class NewsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.SearchNews -> {
                fetchNews(intent.query)
            }
        }
    }

    private fun fetchNews(query: String) {
        viewModelScope.launch {
            // Actualiza el estado a "cargando"
            _uiState.update { it.copy(isLoading = true) }

            val result = runCatching { fetchLatestNews(query) } // Tu función de red

            // Actualiza el estado con el resultado
            _uiState.update {
                result.fold(
                    onSuccess = { response -> it.copy(isLoading = false, articles = response.articles) },
                    onFailure = { error -> it.copy(isLoading = false, error = "Fallo al cargar noticias $error") }
                )
            }
        }
    }
}