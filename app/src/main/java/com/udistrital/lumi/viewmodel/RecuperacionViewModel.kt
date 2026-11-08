package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecuperacionViewModel : ViewModel() {
    // Estados reactivos que las vistas van a observar
    var correo by mutableStateOf("")
    var codigoVerificacion by mutableStateOf("")
    var nuevaContrasena by mutableStateOf("")
    var confirmarContrasena by mutableStateOf("")

    var tiempoRestante by mutableStateOf(59)
    var temporizadorActivo by mutableStateOf(false)

    fun iniciarTemporizador() {
        if (temporizadorActivo) return
        temporizadorActivo = true
        tiempoRestante = 59
        viewModelScope.launch {
            while (tiempoRestante > 0) {
                delay(1000)
                tiempoRestante--
            }
            temporizadorActivo = false
        }
    }

    // Funciones lógicas que conectaremos con Firebase más adelante
    fun enviarCodigo(onExito: () -> Unit) {
        // Aquí irá: FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
        iniciarTemporizador()
        onExito()
    }

    fun validarCodigo(onExito: () -> Unit) {
        // Validamos que el código tenga 4 dígitos antes de avanzar
        if (codigoVerificacion.length == 4) {
            onExito()
        }
    }

    fun actualizarContrasena(onExito: () -> Unit) {
        // Validamos que las contraseñas coincidan y tengan el largo correcto
        if (nuevaContrasena == confirmarContrasena && nuevaContrasena.length >= 6) {
            // Aquí irá la lógica de Firebase para guardar la nueva clave
            onExito()
        }
    }
}