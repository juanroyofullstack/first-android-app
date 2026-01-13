package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.NewsIntent
import com.example.myapplication.model.NewsItem
import com.example.myapplication.ui.components.Header
import com.example.myapplication.ui.screens.AppContent
import com.example.myapplication.ui.screens.LoginForm
import com.example.myapplication.ui.screens.NewsDetail
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyAppNavHost(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState()
    val state = uiState.value
    val navController: NavHostController = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(NewsIntent.LoadInitialNews())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Header(
                onHome = {
                    navController.navigate("home")
                },
                onDetailView = if (state.selectedArticleDetail != null) {
                    { navController.navigate("detail") }
                } else {
                    null
                },
                onProfile = {
                    navController.navigate("profile")
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                AppContent(
                    innerPadding = PaddingValues(),
                    newsState = state,
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
                    news = state.selectedArticleDetail,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("profile") {
                LoginForm(
                    modifier = Modifier.padding(innerPadding), navController = navController
                )
            }
            composable("user") {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp, 46.dp, 16.dp, 16.dp)
                ) {
                    Text(
                        text = "text",
                        modifier = Modifier.padding(top = 64.dp)
                    )
                }
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