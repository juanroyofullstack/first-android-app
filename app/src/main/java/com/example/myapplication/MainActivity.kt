package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.screens.NewsDetail
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.NewsIntent
import com.example.myapplication.model.NewsItem
import com.example.myapplication.ui.components.Header
import com.example.myapplication.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyAppNavHost()
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    viewModel: NewsViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController: NavHostController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(NewsIntent.LoadInitialNews())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(
                onOption1 = {
                    navController.navigate("home")
                },
                onOption2 = {
                    navController.navigate("detail")
                })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                AppContent(
                    innerPadding = PaddingValues(),
                    newsState = uiState,
                    onSearch = { query ->
                        viewModel.handleIntent(NewsIntent.SearchNews(query))
                    },
                    onNewsClick = { article: NewsItem ->
                        viewModel.handleIntent(NewsIntent.SelectedNewsDetail(article))
                        navController.navigate("detail")
                    }
                )
            }
            composable("detail") {
                NewsDetail(
                    modifier = Modifier.padding(innerPadding),
                    news = uiState.selectedArticleDetail,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MyAppNavHost()
    }
}