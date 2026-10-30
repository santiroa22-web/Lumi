package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.udistrital.lumi.data.DatosDePrueba

class HomeViewModel : ViewModel() {

    // Datos del usuario traídos del repositorio/datos de prueba
    var nombreUsuario by mutableStateOf(DatosDePrueba.NOMBRE_USUARIO)
        private set

    var diasRacha by mutableIntStateOf(DatosDePrueba.DIAS_RACHA)
        private set

    // Estado de la selección de ánimo
    var animoSeleccionado by mutableStateOf<Int?>(null)
        private set

    fun seleccionarAnimo(animo: Int, onAnimoSeleccionado: (Int) -> Unit) {
        animoSeleccionado = animo
        onAnimoSeleccionado(animo)
    }
}