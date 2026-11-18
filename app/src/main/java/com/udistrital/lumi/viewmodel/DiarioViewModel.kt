package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NotaDiario(
    val id: Long,
    val titulo: String,
    val contenido: String,
    val fecha: String
)

class DiarioViewModel : ViewModel() {

    var tituloActual by mutableStateOf("")
        private set

    var contenidoActual by mutableStateOf("")
        private set

    // Notas en memoria (incluye ejemplos iniciales para que la libreta luzca viva en la demo)
    val notas = mutableStateListOf(
        NotaDiario(
            id = 1L,
            titulo = "Un respiro entre clases",
            contenido = "Hoy logré terminar el avance de la app con el equipo y me tomé un café tranquilo antes de volver a casa.",
            fecha = "Ayer • 07:15 p. m."
        ),
        NotaDiario(
            id = 2L,
            titulo = "Pequeños logros",
            contenido = "Agradezco tener personas que me apoyan cuando el semestre se pone pesado.",
            fecha = "15 nov • 09:30 p. m."
        )
    )

    fun onTituloChange(nuevoTitulo: String) {
        tituloActual = nuevoTitulo
    }

    fun onContenidoChange(nuevoContenido: String) {
        contenidoActual = nuevoContenido
    }

    fun guardarNota(): Boolean {
        if (contenidoActual.isBlank()) return false

        val tituloFinal = tituloActual.trim().ifEmpty { "Nota de gratitud" }
        val sdf = SimpleDateFormat("dd MMM • hh:mm a", Locale.getDefault())

        notas.add(
            0,
            NotaDiario(
                id = System.currentTimeMillis(),
                titulo = tituloFinal,
                contenido = contenidoActual.trim(),
                fecha = "Hoy • ${sdf.format(Date()).substringAfter("• ")}"
            )
        )

        tituloActual = ""
        contenidoActual = ""
        return true
    }

    fun eliminarNota(nota: NotaDiario) {
        notas.remove(nota)
    }
}