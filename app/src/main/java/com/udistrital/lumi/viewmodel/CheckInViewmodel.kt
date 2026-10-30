package com.udistrital.lumi.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.lumi.data.DatosDePrueba
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Carta(val id: Int, val icono: ImageVector)

class CheckInViewModel : ViewModel() {

    // --- PASO 1: ÁNIMO ---
    var animoSeleccionado by mutableStateOf<String?>(null)
        private set

    fun seleccionarAnimo(opcion: String) {
        animoSeleccionado = opcion
    }

    // --- PASO 2: PAUSA ACTIVA (JUEGO DE MEMORIA) ---
    val cartas: List<Carta> by lazy {
        val iconos = listOf(
            Icons.Default.FavoriteBorder,
            Icons.Default.AutoAwesome,
            Icons.Default.EmojiEvents
        )
        (iconos + iconos).mapIndexed { i, icono -> Carta(i, icono) }.shuffled()
    }

    val encontradas = mutableStateListOf<Int>()
    val volteadas = mutableStateListOf<Int>()

    val juegoTerminado: Boolean
        get() = encontradas.size == cartas.size

    fun voltearCarta(cartaId: Int) {
        val visible = cartaId in encontradas || cartaId in volteadas
        if (!visible && volteadas.size < 2) {
            volteadas.add(cartaId)
            if (volteadas.size == 2) {
                comprobarPareja()
            }
        }
    }

    private fun comprobarPareja() {
        viewModelScope.launch {
            val c1 = cartas.first { it.id == volteadas[0] }
            val c2 = cartas.first { it.id == volteadas[1] }
            if (c1.icono == c2.icono) {
                encontradas.addAll(volteadas)
            } else {
                delay(700)
            }
            volteadas.clear()
        }
    }

    // --- PASO 3: VÍNCULOS ---
    val respuestas = mutableStateMapOf<String, String>()

    val preguntas = listOf(
        "¿Cómo estuvo la relación con tu familia hoy?" to listOf("Muy cercana", "Tranquila", "Hubo tensión", "Me sentí incomprendido"),
        "¿Te sentiste acompañado hoy?" to listOf("Sí, apoyado", "Lo normal", "Me sentí solo"),
        "¿Hubo momentos de mucho estrés?" to listOf("No", "Un poco", "Sí, bastante")
    )

    fun seleccionarRespuesta(pregunta: String, respuesta: String) {
        respuestas[pregunta] = respuesta
    }

    // --- PANTALLA RACHA ---
    val nuevaRacha: Int
        get() = DatosDePrueba.DIAS_RACHA + 1
}