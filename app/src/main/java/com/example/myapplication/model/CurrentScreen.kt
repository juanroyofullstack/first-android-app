package com.example.myapplication.model

import kotlinx.serialization.Serializable

enum class Screen { Home, News }

@Serializable
data class CurrentScreen(
    val currentScreen: Screen = Screen.Home
)

sealed interface CurrentScreenIntent {
    data class SetCurrentScreen(val screen: Screen) : CurrentScreenIntent
}