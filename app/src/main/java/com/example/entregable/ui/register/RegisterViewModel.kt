package com.example.entregable.ui.register

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.entregable.data.DatabaseHelper
import com.example.entregable.model.User

class RegisterViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun register(context: Context, onSuccess: () -> Unit) {
        if (username.isBlank() || password.isBlank()) {
            errorMessage = "Completa todos los campos"
            return
        }

        if (password != confirmPassword) {
            errorMessage = "Las contraseñas no coinciden"
            return
        }

        val db = DatabaseHelper(context)
        val user = User(username = username, password = password)
        val success = db.registerUser(user)

        if (success) {
            errorMessage = null
            onSuccess()
        } else {
            errorMessage = "El usuario ya existe o hubo un error"
        }
    }
}