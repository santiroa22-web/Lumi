package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.udistrital.lumi.data.DatosDePrueba
import com.udistrital.lumi.data.DiaSemana

class CalendarioViewModel : ViewModel() {

    val semana: List<DiaSemana> = DatosDePrueba.semana

    var diaSeleccionado by mutableStateOf(semana.last())
        private set

    val diasRacha: Int = DatosDePrueba.DIAS_RACHA

    fun seleccionarDia(dia: DiaSemana) {
        diaSeleccionado = dia
    }

    fun obtenerTextoDetalle(animo: Int): String {
        return when (animo) {
            0, 1 -> "Día difícil, está bien no estar bien"
            2 -> "Día tranquilo y neutral"
            3, 4 -> "Clima emocional tranquilo"
            else -> "Aún no has hecho tu registro de hoy"
        }
    }
}