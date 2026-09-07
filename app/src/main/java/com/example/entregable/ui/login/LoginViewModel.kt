package com.example.entregable.ui.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.entregable.data.DatabaseHelper
import com.example.entregable.data.SessionManager

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun login(context: Context, onSuccess: () -> Unit) {
        val db = DatabaseHelper(context)
        if (db.validateUser(username, password)) {
            SessionManager(context).saveSession(username)
            errorMessage = null
            onSuccess()
        } else {
            errorMessage = "Usuario o contraseña incorrectos"
        }
    }
}