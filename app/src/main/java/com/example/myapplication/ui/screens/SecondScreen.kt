package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.model.NewsResponse

@Composable
fun SecondScreen(modifier: Modifier = Modifier, news: NewsResponse? = null) {
    Surface(modifier = modifier.padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            val firstArticle = news?.articles?.getOrNull(0)
            val title = firstArticle?.title

            if (!title.isNullOrBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news?.articles[0]?.author ?: "Cargando...",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = news?.articles[0]?.author?: "Cargando...",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Desde su lanzamiento estable, Jetpack Compose ha cambiado las reglas del juego para los desarrolladores de Android. " +
                        "La capacidad de construir interfaces de usuario de forma declarativa utilizando Kotlin no solo ha simplificado el proceso, " +
                        "sino que también ha mejorado el rendimiento y la mantenibilidad del código.\n\n" +
                        "A diferencia del antiguo sistema basado en XML, donde los layouts se inflaban y se manipulaban imperativamente, " +
                        "Compose describe cómo debería ser la UI en un estado determinado. Cuando el estado de la aplicación cambia, " +
                        "el framework se encarga de actualizar automáticamente solo las partes de la UI que lo necesitan, un proceso conocido como 'recomposición'.\n\n" +
                        "Esta aproximación reduce drásticamente el código repetitivo y elimina la necesidad de usar `findViewById` y otras APIs propensas a errores. " +
                        "La comunidad de desarrolladores ha adoptado masivamente esta tecnología, y cada vez más aplicaciones en la Play Store migran sus vistas a Compose.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    MyApplicationTheme {
        SecondScreen()
    }
}