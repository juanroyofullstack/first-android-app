package com.example.myapplication.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    onHome: () -> Unit = {},
    onProfile: () -> Unit = {},
    onDetailView: (() -> Unit)? = null
) {
    return (
            TopAppBar(
                title = { Text("News searcher App") },
                navigationIcon = {
                    HamburgerMenu(
                        onHome,
                        onDetailView,
                    )

                },
                actions = {
                    UserHeaderIcon(onProfile)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
            )
}