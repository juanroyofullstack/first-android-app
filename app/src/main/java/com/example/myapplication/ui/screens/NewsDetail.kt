package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.NewsItem
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun NewsDetail(modifier: Modifier = Modifier, news: NewsItem? = null, onBack: () -> Unit) {
    Surface(modifier = modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!news?.title.isNullOrBlank()) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            if (!news?.author.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = news.author,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (!news?.description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            if (!news?.author.isNullOrBlank() && !news.date.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = news.author,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .height(16.dp)
                            .width(1.dp)
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                    Text(
                        text = news.date,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            if (!news?.content.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = news.content,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    MyApplicationTheme {
        NewsDetail(onBack = {})
    }
}