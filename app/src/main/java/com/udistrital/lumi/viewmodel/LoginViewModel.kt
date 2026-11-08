package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var cargando by mutableStateOf(false)
        private set

    // Bandera de navegación segura
    var navegarAInicio by mutableStateOf(false)
        private set

    fun iniciarSesion(
        correo: String,
        contrasena: String,
        onError: (String) -> Unit
    ) {
        if (cargando) return

        if (correo.isEmpty() || contrasena.isEmpty()) {
            onError("Por favor, completa todos los campos.")
            return
        }

        cargando = true

        auth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                cargando = false
                if (task.isSuccessful) {
                    // Activamos la bandera para que la UI navegue solita
                    navegarAInicio = true
                } else {
                    onError(task.exception?.localizedMessage ?: "Credenciales incorrectas")
                }
            }
    }

    // Para resetear la bandera después de navegar
    fun reiniciarNavegacion() {
        navegarAInicio = false
    }
}