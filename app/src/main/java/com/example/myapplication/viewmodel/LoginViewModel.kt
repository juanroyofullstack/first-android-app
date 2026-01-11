package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoginIntent
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> onLogin(intent.email, intent.password)
            is LoginIntent.Logout -> onLogout()
            is LoginIntent.EmailChange -> onEmailChange(intent.email)
            is LoginIntent.PasswordChange -> onPasswordChange(intent.password)
        }
    }

    fun onEmailChange(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
    }

    fun onLogin(email: String, password: String) {
        _loginState.value =
            _loginState.value.copy(email = email, password = password, isLoading = true)

        viewModelScope.launch {
            delay(3000L)

            val success = email.isNotBlank() && password.isNotBlank()
            _loginState.value = _loginState.value.copy(
                isLoading = false,
                isLoggedIn = success,
                loginResponse = LoginResponse(success = true, message = "Inicio de sesión exitoso"),
                error = if (success) "" else "Credenciales inválidas"
            )
        }
    }

    fun onLogout() {
        _loginState.value = _loginState.value.copy(
            email = "",
            password = "",
            isLoggedIn = false,
            error = ""
        )
    }
}
