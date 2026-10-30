package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun login(onSuccess: () -> Unit) {
        // Credenciales de prueba actualizadas
        if (email.trim() == "santiago@prueba.com" && password == "123456") {
            errorMessage = null
            onSuccess()
        } else {
            errorMessage = "Credenciales incorrectas"
        }
    }
}