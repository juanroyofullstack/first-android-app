package com.example.myapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHeaderIcon(onProfile: () -> Unit) {
    IconButton(onClick = { onProfile() }) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "User Profile"
        )
    }
}