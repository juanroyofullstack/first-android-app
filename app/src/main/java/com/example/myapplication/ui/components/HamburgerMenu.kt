package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HamburgerMenu(
    onOption1: () -> Unit = {},
    onOption2: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú hamburguesa"
            )
        }

        DropdownMenu(
            expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Home") },
                onClick = {
                    onOption1()
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Segunda vista") },
                onClick = {
                    onOption2()
                    expanded = false
                }
            )
        }
    }
}