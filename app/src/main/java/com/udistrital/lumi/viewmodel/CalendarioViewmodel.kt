package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class DiaCalendario(
    val numero: Int,
    val diaSemana: String,
    val animo: Int, // 0: Muy mal, 1: Triste, 2: Neutral, 3: Bien, 4: Muy bien, -1: Sin registro
    val esHoy: Boolean = false,
    val esFuturo: Boolean = false
)

data class EstadisticasMes(
    val promedioTexto: String,
    val emocionDominante: String,
    val porcentajeConstancia: String,
    val etiquetaBalance: String,
    val conteoPorAnimo: List<Int>,
    val totalRegistrados: Int
)

class CalendarioViewModel : ViewModel() {

    // 0 = Octubre 2026, 1 = Noviembre 2026 (Actual), 2 = Diciembre 2026
    var indiceMes by mutableIntStateOf(1)
        private set

    var diaSeleccionadoNumero by mutableIntStateOf(17)
        private set

    val nombresMeses = listOf("Octubre 2026", "Noviembre 2026", "Diciembre 2026")
    private val diasPorMes = listOf(31, 30, 31)
    private val offsetInicioMes = listOf(3, 6, 1)

    // Histórico base de octubre y primeros días de noviembre (antes de la racha actual)
    private val historialOctubre = listOf(
        3, 4, 3, 2, 3, 4, 4, 2, 1, 2, 3, 3, 4, 4, 3,
        2, 3, 3, 4, 2, 3, 4, 4, 3, 3, 2, 3, 4, 3, 4, 3
    )
    // Emociones de referencia para los días previos a hoy en noviembre (días 1..16)
    private val emocionesPreviasNov = listOf(
        3, 4, 2, 3, 4, 3, 2, 3, 4, 3, 2, 3, 4, 3, 4, 3
    )

    val nombreMesActual: String
        get() = nombresMeses[indiceMes]

    val offsetPrimerDia: Int
        get() = offsetInicioMes[indiceMes]

    fun mesAnterior() {
        if (indiceMes > 0) {
            indiceMes--
            diaSeleccionadoNumero = 1
        }
    }

    fun mesSiguiente() {
        if (indiceMes < nombresMeses.lastIndex) {
            indiceMes++
            diaSeleccionadoNumero = 1
        }
    }

    fun seleccionarDia(numeroDia: Int) {
        diaSeleccionadoNumero = numeroDia
    }

    fun construirDiasDelMes(
        animoHoyHome: Int?,
        rachaCompletadaHoy: Boolean,
        diasRachaActual: Int
    ): List<DiaCalendario> {
        val totalDias = diasPorMes[indiceMes]
        val offset = offsetInicioMes[indiceMes]
        val letrasSemana = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

        // Días anteriores al 17 que corresponden a la racha activa
        val diasPreviosDeRacha = if (rachaCompletadaHoy) {
            (diasRachaActual - 1).coerceIn(0, 16)
        } else {
            diasRachaActual.coerceIn(0, 16)
        }
        val primerDiaRachaActiva = 17 - diasPreviosDeRacha

        return (1..totalDias).map { dia ->
            val diaSemanaNombre = letrasSemana[(offset + dia - 1) % 7]
            val esHoy = (indiceMes == 1 && dia == 17)
            val esFuturo = (indiceMes == 1 && dia > 17) || (indiceMes == 2)

            val animoReal = when {
                indiceMes == 0 -> historialOctubre[dia - 1]
                indiceMes == 2 -> -1
                esHoy -> {
                    if (rachaCompletadaHoy) {
                        animoHoyHome ?: 2
                    } else {
                        -1
                    }
                }
                esFuturo -> -1
                // Días 1..16 de Noviembre: muestra registrados exactamente los días que conforman la racha del Home
                dia >= primerDiaRachaActiva -> emocionesPreviasNov[dia - 1]
                else -> -1
            }

            DiaCalendario(
                numero = dia,
                diaSemana = diaSemanaNombre,
                animo = animoReal,
                esHoy = esHoy,
                esFuturo = esFuturo
            )
        }
    }

    fun calcularEstadisticas(dias: List<DiaCalendario>): EstadisticasMes {
        val registrados = dias.filter { it.animo in 0..4 }
        val conteo = List(5) { nivel -> registrados.count { it.animo == nivel } }

        if (registrados.isEmpty()) {
            return EstadisticasMes(
                promedioTexto = "Sin datos",
                emocionDominante = "Pendiente",
                porcentajeConstancia = "0%",
                etiquetaBalance = "Sin registros",
                conteoPorAnimo = conteo,
                totalRegistrados = 0
            )
        }

        val promedio = registrados.map { it.animo }.average()
        val promedioTexto = when {
            promedio >= 3.5 -> "Muy positivo"
            promedio >= 2.6 -> "Positivo"
            promedio >= 1.8 -> "Equilibrado"
            else -> "Sensible"
        }

        val etiquetaBalance = when {
            promedio >= 3.2 -> "Mes Positivo"
            promedio >= 2.0 -> "Mes Equilibrado"
            else -> "Autocuidado"
        }

        val nivelMasFrecuente = conteo.indices.maxByOrNull { conteo[it] } ?: 2
        val emocionDominante = when (nivelMasFrecuente) {
            4 -> "Alegría"
            3 -> "Calma"
            2 -> "Neutral"
            1 -> "Tristeza"
            else -> "Estrés"
        }

        val diasTranscurridos = dias.count { !it.esFuturo }.coerceAtLeast(1)
        val porcentaje = ((registrados.size * 100) / diasTranscurridos).coerceIn(0, 100)

        return EstadisticasMes(
            promedioTexto = promedioTexto,
            emocionDominante = emocionDominante,
            porcentajeConstancia = "$porcentaje% (${registrados.size}/$diasTranscurridos)",
            etiquetaBalance = etiquetaBalance,
            conteoPorAnimo = conteo,
            totalRegistrados = registrados.size
        )
    }

    fun obtenerTextoDetalle(dia: DiaCalendario): String {
        if (dia.esFuturo) return "Día próximo • Aún por vivir"
        return when (dia.animo) {
            0 -> "Día muy difícil • Recuerda ser amable contigo"
            1 -> "Día triste o pesado • Está bien no estar bien"
            2 -> "Día tranquilo y neutral"
            3 -> "Buen ánimo y clima emocional estable"
            4 -> "Excelente día • Energía y bienestar altos"
            else -> "Aún no has registrado tu emoción de este día"
        }
    }
}