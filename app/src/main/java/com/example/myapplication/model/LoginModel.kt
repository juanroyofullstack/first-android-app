package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val loginResponse: LoginResponse = LoginResponse(),
    val message: String = ""
)

@Serializable
data class LoginResponse(
    val success: Boolean = false,
    val message: String = ""
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

sealed interface LoginIntent {
    data class EmailChange(val email: String) : LoginIntent
    data class PasswordChange(val password: String) : LoginIntent
    data class Login(val email: String, val password: String) : LoginIntent
    data class Logout(val dummy: Unit = Unit) : LoginIntent
}
