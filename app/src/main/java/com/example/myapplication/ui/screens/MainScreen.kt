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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.components.Header
import com.example.myapplication.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

val apiKey = BuildConfig.API_KEY
private suspend fun fetchLatestNews(): String = withContext(Dispatchers.IO) {
    val connection = URL("https://gnews.io/api/v4/search?q=Google&lang=en&max=5&apikey=$apiKey").openConnection() as HttpURLConnection
    connection.inputStream.bufferedReader().use { it.readText() }
}

enum class Screen { Home, Second }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(Screen.Home) }
    var news by rememberSaveable { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loading = true
        news = runCatching { fetchLatestNews() }.getOrNull()
        print(news)
        loading = false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(onOption1 = {
                setCurrentScreen(Screen.Home)
            }, onOption2 = {
                setCurrentScreen(Screen.Second)
            })
        }
    ) { innerPadding ->
        when (currentScreen) {
            Screen.Home -> AppContent(innerPadding)
            Screen.Second -> {
                SecondScreen(Modifier.padding(innerPadding))
                when {
                    loading -> CircularProgressIndicator()
                    news != null -> Text(news!!)
                    else -> Text("No se pudo cargar la noticia")
                }
            }
        }
    }
}