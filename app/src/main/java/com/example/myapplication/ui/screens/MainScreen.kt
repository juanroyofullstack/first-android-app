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
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.components.Header
import com.example.myapplication.model.NewsIntent
import com.example.myapplication.viewmodel.NewsViewModel

enum class Screen { Home, News }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: NewsViewModel = viewModel()) {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(Screen.Home) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(NewsIntent.LoadInitialNews())
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
            Screen.Home -> AppContent(
                innerPadding = PaddingValues(),
                newsState = uiState,
                onSearch = { query ->
                    viewModel.handleIntent(NewsIntent.SearchNews(query))
                }
            )

            Screen.News -> {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }
                    uiState.articles.firstOrNull() != null -> {
                        NewsDetail(
                            modifier = Modifier.padding(innerPadding),
                            news = uiState.articles[0]
                        )
                    }
                    else -> Text("No se pudo cargar la noticia")
                }
            }
        }
    }
}