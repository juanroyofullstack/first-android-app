package com.example.myapplication.model

data class UserSession(
    val isLoggedIn: Boolean = false,
    val email: String = "",
    val name: String? = ""
)