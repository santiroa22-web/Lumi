package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MascotaViewModel : ViewModel() {

    // Estados de las barras de estadísticas (privados para modificación directa)
    var nutricion by mutableIntStateOf(85)
        private set

    var felicidad by mutableIntStateOf(92)
        private set

    var energia by mutableIntStateOf(78)
        private set

    var bienestar by mutableIntStateOf(95)
        private set

    // Estado derivado según el promedio
    val promedio: Int
        get() = (nutricion + felicidad + energia + bienestar) / 4

    val estado: String
        get() = when {
            promedio >= 85 -> "radiante"
            promedio >= 60 -> "contento"
            else -> "cansado"
        }

    // Acciones de la mascota
    fun alimentar() {
        nutricion = minOf(100, nutricion + 5)
    }

    fun jugar() {
        felicidad = minOf(100, felicidad + 5)
        energia = maxOf(0, energia - 3)
    }

    fun pasear() {
        bienestar = minOf(100, bienestar + 5)
        energia = maxOf(0, energia - 3)
    }
}