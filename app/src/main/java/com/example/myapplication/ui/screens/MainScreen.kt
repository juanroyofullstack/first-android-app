package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.components.Header
import com.example.myapplication.model.NewsIntent
import com.example.myapplication.model.NewsUiState


//private val json = Json { ignoreUnknownKeys = true }
//
//private val logging = HttpLoggingInterceptor().apply {
//    if(BuildConfig.DEBUG) {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//}
//
//private val client = OkHttpClient.Builder()
//    .addInterceptor(logging)
//    .build()
//
//private val apiKey = BuildConfig.API_KEY
//
//private suspend fun fetchLatestNews(query: String = "Google"): NewsUiState = withContext(Dispatchers.IO) {
//    Log.d("NewsFetch", "Llamando a gnews con OkHttp")
//    val request = Request.Builder()
//        .url("https://gnews.io/api/v4/search?q=$query&lang=en&max=5&apikey=$apiKey")
//        .build()
//
//    client.newCall(request).execute().use { response ->
//        if (!response.isSuccessful) error("HTTP error ${response.code}: ${response.message}")
//        val body = response.body?.string().orEmpty()
//        json.decodeFromString<NewsUiState>(body)
//    }
//}

enum class Screen { Home, News }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: NewsViewModel = viewModel()) {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(Screen.Home) }
    var news by rememberSaveable { mutableStateOf<NewsUiState?>(null) }
    var loading by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

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
            Screen.Home -> AppContent(
                innerPadding = PaddingValues(),
                newsState = uiState,
                onSearch = { query ->
                    viewModel.handleIntent(NewsIntent.SearchNews(query))
                }
            )

            Screen.News -> {
                when {
                    loading -> {
                        CircularProgressIndicator()
                    }
                    news?.articles?.firstOrNull() != null -> {
                        NewsDetail(
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