package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding) // Aplica el padding del Scaffold
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp) // Padding interno adicional
    ) {
        Text(
            text = "Contenido de ejemplo"
        )
        // Aquí podrías añadir más elementos de UI
    }
}