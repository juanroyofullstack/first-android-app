package com.example.myapplication.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "user_session")

class UserSessionDataStore(private val context: Context) {
    private object Keys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val EMAIL = stringPreferencesKey("email")
        val NAME = stringPreferencesKey("name")
    }

    val sessionFlow: Flow<UserSession> =
        context.dataStore.data.map { prefs ->
            UserSession(
                isLoggedIn = prefs[Keys.IS_LOGGED_IN] ?: false,
                email = prefs[Keys.EMAIL] ?: "",
                name = prefs[Keys.NAME] ?: ""
            )
        }

    suspend fun saveSession(session: UserSession) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = session.isLoggedIn
            prefs[Keys.EMAIL] = session.email
            prefs[Keys.NAME] = session.name ?: ""
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}