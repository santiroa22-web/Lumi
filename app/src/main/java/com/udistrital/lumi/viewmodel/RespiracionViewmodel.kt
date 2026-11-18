package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class FaseRespiracion(val titulo: String, val subtitulo: String, val segundos: Int, val escalaObjetivo: Float) {
    LISTO("¿Listo para pausar?", "Toma un momento solo para ti", 0, 0.55f),
    INHALA("Inhala profundo...", "Llena tus pulmones despacio por la nariz", 4, 1.0f),
    SOSTEN("Sostén el aire...", "Siente la calma en tu pecho", 3, 1.0f),
    EXHALA("Exhala suave...", "Suelta toda la tensión por la boca", 4, 0.50f),
    COMPLETADO("¡Lo hiciste increíble!", "Tu mente y tu cuerpo te lo agradecen", 0, 0.75f)
}

class RespiracionViewModel : ViewModel() {

    var faseActual by mutableStateOf(FaseRespiracion.LISTO)
        private set

    var segundosRestantes by mutableIntStateOf(0)
        private set

    var cicloActual by mutableIntStateOf(1)
        private set

    val totalCiclos = 2 // 2 ciclos = 22 segundos en total (ideal para la demo en vivo)

    var enEjecucion by mutableStateOf(false)
        private set

    var progresoTotal by mutableFloatStateOf(0f)
        private set

    private var timerJob: Job? = null

    private val mensajesFinalesLumi = listOf(
        "¡Muy bien! Mantén la calma, recuerda que Lumi te acompaña en tu proceso. Un paso a la vez, todo va a estar bien 🌿",
        "Respira hondo y suelta lo que no puedes controlar hoy. Aquí estoy contigo en cada paso de tu camino 💚",
        "¡Excelente pausa! Tu bienestar es prioridad. Recuerda que Lumi siempre está aquí para acompañarte y escucharte ✨"
    )

    var mensajeFinalElegido by mutableStateOf(mensajesFinalesLumi.first())
        private set

    fun iniciarEjercicio() {
        if (enEjecucion) return
        enEjecucion = true
        cicloActual = 1
        progresoTotal = 0f
        mensajeFinalElegido = mensajesFinalesLumi.random()

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            val fasesPorCiclo = listOf(
                FaseRespiracion.INHALA,
                FaseRespiracion.SOSTEN,
                FaseRespiracion.EXHALA
            )
            val segundosTotalesEjercicio = totalCiclos * fasesPorCiclo.sumOf { it.segundos }
            var segundosTranscurridos = 0

            for (ciclo in 1..totalCiclos) {
                cicloActual = ciclo
                for (fase in fasesPorCiclo) {
                    faseActual = fase
                    for (seg in fase.segundos downTo 1) {
                        segundosRestantes = seg
                        delay(1000L)
                        segundosTranscurridos++
                        progresoTotal = segundosTranscurridos.toFloat() / segundosTotalesEjercicio.toFloat()
                    }
                }
            }

            segundosRestantes = 0
            progresoTotal = 1f
            faseActual = FaseRespiracion.COMPLETADO
            enEjecucion = false
        }
    }

    fun detenerEjercicio() {
        timerJob?.cancel()
        enEjecucion = false
        faseActual = FaseRespiracion.LISTO
        segundosRestantes = 0
        cicloActual = 1
        progresoTotal = 0f
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}