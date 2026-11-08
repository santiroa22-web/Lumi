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

    var navegarAOnboarding by mutableStateOf(false) // O navegarAInicio dependiendo de tu ruta
        private set

    var navegarAInicio by mutableStateOf(false)
        private set

    // Control de intentos fallidos
    var intentosFallidos by mutableStateOf(0)
        private set

    var estaBloqueado by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    fun iniciarSesion(
        correo: String,
        contrasena: String
    ) {
        if (cargando || estaBloqueado) return

        if (correo.isBlank() || contrasena.isBlank()) {
            mensajeError = "Por favor, completa todos los campos."
            return
        }

        cargando = true
        mensajeError = null

        auth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener { task ->
                cargando = false
                if (task.isSuccessful) {
                    // Si acierta, reiniciamos los intentos fallidos y dejamos entrar
                    intentosFallidos = 0
                    navegarAInicio = true
                } else {
                    // Si falla, sumamos un intento fallido
                    intentosFallidos++

                    if (intentosFallidos >= 5) {
                        estaBloqueado = true
                        mensajeError = "Demasiados intentos fallidos. Espera 5 minutos para ingresar."
                        // Opcional: Aquí podrías programar un Handler o corrutina para desbloquear a los 5 min
                    } else {
                        // Mensaje claro de credenciales incorrectas o error de Firebase
                        mensajeError = "Correo o contraseña incorrectos. (Intentos: $intentosFallidos/5)"
                    }
                }
            }
    }

    fun reiniciarNavegacion() {
        navegarAInicio = false
    }
}