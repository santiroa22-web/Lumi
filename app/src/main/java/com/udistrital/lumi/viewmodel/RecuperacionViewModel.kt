package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RecuperacionViewModel : ViewModel() {
    var correo by mutableStateOf("")
    private val auth = FirebaseAuth.getInstance()

    fun enviarEnlaceRecuperacion(onExito: () -> Unit, onError: (String) -> Unit) {
        if (correo.isEmpty() || !correo.contains("@")) {
            onError("Ingresa un correo electrónico válido.")
            return
        }

        // Firebase se encarga de enviar el correo con el link de restablecimiento
        auth.sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onExito() // Salta a la pantalla de "Correo Enviado"
                } else {
                    onError(task.exception?.localizedMessage ?: "No se pudo enviar el enlace.")
                }
            }
    }
}