package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



class CurrentScreenModel : ViewModel() {
        private val _currentScreen = MutableStateFlow(Screen.Home)
        val currentScreen: StateFlow<Screen> = _currentScreen

        fun setScreen(screen: Screen) {
            _currentScreen.value = screen
        }
}