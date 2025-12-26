package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import android.util.Log
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.components.Header
import com.example.myapplication.BuildConfig
import com.example.myapplication.model.NewsResponse
import kotlinx.coroutines.Dispatchers
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

private suspend fun fetchLatestNews(): NewsResponse = withContext(Dispatchers.IO) {
    Log.d("NewsFetch", "Llamando a gnews con OkHttp")
    val request = Request.Builder()
        .url("https://gnews.io/api/v4/search?q=Google&lang=en&max=5&apikey=$apiKey")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) error("HTTP error ${response.code}: ${response.message}")
        val body = response.body?.string().orEmpty()
        json.decodeFromString<NewsResponse>(body)
    }
}

enum class Screen { Home, News }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(Screen.Home) }
    var news by rememberSaveable { mutableStateOf<NewsResponse?>(null) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loading = true
        val result = runCatching { fetchLatestNews() }
            .onFailure { e -> Log.e("MainScreen", "Error al obtener news", e) }
            .getOrNull()
        news = result
        loading = false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(
            onOption1 = {
                setCurrentScreen(Screen.Home)
            }, onOption2 = {
                setCurrentScreen(Screen.News)
            })
        }
    ) { innerPadding ->
        when (currentScreen) {
            Screen.Home -> AppContent(innerPadding)
            Screen.News -> {
                when {
                    loading -> {
                        CircularProgressIndicator()
                    }
                    news?.articles?.firstOrNull() != null -> {
                        SecondScreen(
                            modifier = Modifier.padding(innerPadding),
                            news = news?.articles[0]
                        )
                    }
                    else -> Text("No se pudo cargar la noticia")
                }
            }
        }
    }
}