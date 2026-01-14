package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.LoginIntent
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.LoginUiState
import com.example.myapplication.model.UserSession
import com.example.myapplication.repository.UserSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    init {
        viewModelScope.launch {
            userSessionRepository.sessionFlow.collect { session ->
                _loginState.value = _loginState.value.copy(isLoggedIn = session.isLoggedIn)
                _loginState.value = _loginState.value.copy(email = session.email)
            }
        }
    }

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
            _loginState.value.copy(isLoading = true)

        viewModelScope.launch {
            delay(3000L)

            val success = email.isNotBlank() && password.isNotBlank()
            _loginState.value = _loginState.value.copy(
                email = email,
                password = password,
                isLoading = false,
                isLoggedIn = success,
                loginResponse = LoginResponse(
                    success = success,
                    message = if (success) "Inicio de sesión exitoso" else "Error"
                ),
                error = if (success) "" else "Credenciales inválidas"
            )

            if (success) {
                viewModelScope.launch {
                    userSessionRepository.saveSession(
                        UserSession(
                            isLoggedIn = true,
                            email = email,
                        )
                    )
                }
            }
        }
    }

    fun onLogout() {
        _loginState.value = _loginState.value.copy(
            email = "",
            password = "",
            isLoggedIn = false,
            error = ""
        )

        viewModelScope.launch {
            userSessionRepository.clearSession()
        }
    }
}
