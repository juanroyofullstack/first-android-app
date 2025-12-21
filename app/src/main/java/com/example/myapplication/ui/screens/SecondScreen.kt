package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SecondScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.padding(16.dp)) {
        Text(text = "Otra vista / segunda pantalla")
    }
}