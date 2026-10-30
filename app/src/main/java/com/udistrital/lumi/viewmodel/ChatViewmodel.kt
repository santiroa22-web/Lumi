package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.udistrital.lumi.data.DatosDePrueba
import com.udistrital.lumi.data.MensajeChat

class ChatViewModel : ViewModel() {

    // Lista de mensajes reactiva en memoria
    val mensajes = mutableStateListOf<MensajeChat>().apply {
        addAll(DatosDePrueba.mensajesIniciales)
    }

    var textoNuevo by mutableStateOf("")
        private set

    private var contadorRespuestas by mutableIntStateOf(0)

    fun onTextoChange(nuevoTexto: String) {
        textoNuevo = nuevoTexto
    }

    fun enviarMensaje() {
        if (textoNuevo.isNotBlank()) {
            val mensajeUsuario = textoNuevo.trim()
            mensajes.add(MensajeChat(mensajeUsuario, false, "Ahora"))

            // Respuesta simulada de Lumi
            val respuesta = DatosDePrueba.respuestasLumi[contadorRespuestas % DatosDePrueba.respuestasLumi.size]
            mensajes.add(MensajeChat(respuesta, true, "Ahora"))

            contadorRespuestas++
            textoNuevo = ""
        }
    }
}