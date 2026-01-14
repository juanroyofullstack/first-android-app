package com.example.myapplication.repository

import com.example.myapplication.model.UserSession
import com.example.myapplication.session.UserSessionDataStore
import kotlinx.coroutines.flow.Flow

class UserSessionRepository(private val dataStore: UserSessionDataStore) {
    val sessionFlow: Flow<UserSession> = dataStore.sessionFlow

    suspend fun saveSession(session: UserSession) {
        dataStore.saveSession(session)
    }

    suspend fun clearSession() {
        dataStore.clearSession()
    }
}