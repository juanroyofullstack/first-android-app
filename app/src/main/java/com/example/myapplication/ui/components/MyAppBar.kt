package com.example.myapplication.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar() {

    TopAppBar(
        title = { Text("Mi Header") },
        navigationIcon = {
            HamburgerMenu(
                onOption1 = {
                    print("Hola 1")
                },
                onOption2 = {
                    println("Hola 2")
                },
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}