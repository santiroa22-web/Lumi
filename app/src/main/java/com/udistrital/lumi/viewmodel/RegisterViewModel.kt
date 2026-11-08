package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var cargando by mutableStateOf(false)
        private set

    var navegarAOnboarding by mutableStateOf(false)
        private set

    // Validación para que el nombre no contenga caracteres especiales
    fun esNombreValido(input: String): Boolean {
        val regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$".toRegex()
        return input.isEmpty() || input.matches(regex)
    }

    fun registrarUsuario(
        nombreInput: String,
        correoInput: String,
        contrasenaInput: String,
        aceptaTerminosInput: Boolean,
        onExito: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (cargando) return

        // Validaciones previas exigidas
        if (nombreInput.isBlank() || correoInput.isBlank() || contrasenaInput.isBlank()) {
            onError("Por favor, completa todos los campos.")
            return
        }
        if (!correoInput.contains("@")) {
            onError("Ingresa un correo electrónico válido.")
            return
        }
        if (contrasenaInput.length < 6) {
            onError("La contraseña debe tener al menos 6 caracteres.")
            return
        }
        if (!aceptaTerminosInput) {
            onError("Debes aceptar los términos y condiciones.")
            return
        }

        cargando = true

        // Registro real en Firebase Authentication
        auth.createUserWithEmailAndPassword(correoInput, contrasenaInput)
            .addOnCompleteListener { task ->
                cargando = false
                if (task.isSuccessful) {
                    navegarAOnboarding = true
                    onExito()
                } else {
                    onError(task.exception?.localizedMessage ?: "Error al registrar la cuenta")
                }
            }
    }

    fun reiniciarNavegacion() {
        navegarAOnboarding = false
    }
}