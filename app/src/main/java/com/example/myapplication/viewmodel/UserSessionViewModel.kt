package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.UserSession
import com.example.myapplication.repository.UserSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserSessionViewModel @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userInfo = MutableStateFlow(UserSession())
    val userInfo: StateFlow<UserSession> = _userInfo.asStateFlow()

    init {
        viewModelScope.launch {
            userSessionRepository.sessionFlow.collect { session ->
                _isLoggedIn.value = session.isLoggedIn
                _userInfo.value = UserSession(
                    email = session.email,
                    name = session.name
                )
            }
        }
    }

    fun loginSuccess(email: String, name: String) {
        viewModelScope.launch {
            userSessionRepository.saveSession(
                UserSession(
                    isLoggedIn = true,
                    email = email,
                    name = name
                )
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userSessionRepository.clearSession()
        }
    }
}