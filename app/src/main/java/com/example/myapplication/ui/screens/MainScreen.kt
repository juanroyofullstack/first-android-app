package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.components.MyAppBar

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyAppBar()
        }
    ) { innerPadding ->
        AppContent(innerPadding = innerPadding)
    }
}