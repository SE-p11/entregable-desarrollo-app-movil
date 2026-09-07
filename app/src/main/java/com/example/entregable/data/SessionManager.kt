package com.example.entregable.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_session", Context.MODE_PRIVATE)

    fun saveSession(username: String) {
        prefs.edit().putString("LOGGED_USER", username).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getString("LOGGED_USER", null) != null
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}