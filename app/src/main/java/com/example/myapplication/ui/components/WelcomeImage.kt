package com.example.myapplication.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ImageWelcome() {
        Text(
            text = "Contenido de imagen",
            color = MaterialTheme.colorScheme.onBackground
        )
}