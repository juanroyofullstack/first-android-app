package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.AppContent
import com.example.myapplication.ui.components.MyAppBar

enum class Screen { Home, Second }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf(Screen.Home) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyAppBar(onOption1 = {
                setCurrentScreen(Screen.Home)
            }, onOption2 = {
                setCurrentScreen(Screen.Second)
            })
        }
    ) { innerPadding ->
        when (currentScreen) {
            Screen.Home -> AppContent(innerPadding)
            Screen.Second -> SecondScreen(Modifier.padding(innerPadding))
        }
    }
}