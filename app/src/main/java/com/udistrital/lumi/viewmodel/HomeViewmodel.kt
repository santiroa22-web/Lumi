package com.udistrital.lumi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.udistrital.lumi.model.DatosDePrueba

class HomeViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var nombreUsuario by mutableStateOf(extraerNombreDeUsuario(auth.currentUser))
        private set

    private val authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        nombreUsuario = extraerNombreDeUsuario(firebaseAuth.currentUser)
    }

    init {
        auth.addAuthStateListener(authListener)
    }

    override fun onCleared() {
        super.onCleared()
        auth.removeAuthStateListener(authListener)
    }

    var diasRacha by mutableIntStateOf(DatosDePrueba.DIAS_RACHA)
        private set

    var checkInCompletadoHoy by mutableStateOf(false)
        private set

    var rachaPerdida by mutableStateOf(false)
        private set

    var mostrarDetalleRacha by mutableStateOf(true)
        private set

    // Calendario de 2 semanas en el Home (14 días, índice 8 = Hoy)
    val etiquetasDias = listOf(
        "L", "M", "X", "J", "V", "S", "D",
        "L", "M", "X", "J", "V", "S", "D"
    )
    val indiceHoy = 8

    val diasCompletados = mutableStateListOf<Boolean>().apply {
        val previos = DatosDePrueba.DIAS_RACHA.coerceIn(0, 8)
        for (i in 0 until 14) {
            add(i in (indiceHoy - previos) until indiceHoy)
        }
    }

    var animoSeleccionado by mutableStateOf<Int?>(null)
        private set

    private fun extraerNombreDeUsuario(user: com.google.firebase.auth.FirebaseUser?): String {
        val display = user?.displayName?.trim()
        if (!display.isNullOrEmpty()) {
            return display.split(" ").first().replaceFirstChar { it.uppercase() }
        }
        val email = user?.email?.trim()
        if (!email.isNullOrEmpty() && email.contains("@")) {
            val aliasCorreo = email.substringBefore("@")
                .substringBefore(".")
                .replace(Regex("[0-9]"), "")
                .trim()
            if (aliasCorreo.isNotEmpty()) {
                return aliasCorreo.replaceFirstChar { it.uppercase() }
            }
            return email.substringBefore("@").replaceFirstChar { it.uppercase() }
        }
        return DatosDePrueba.NOMBRE_USUARIO
    }

    fun actualizarNombreUsuario(nombreOCorreo: String) {
        val limpio = nombreOCorreo.trim()
        if (limpio.isNotEmpty()) {
            val base = if (limpio.contains("@")) {
                limpio.substringBefore("@").substringBefore(".").replace(Regex("[0-9]"), "")
            } else {
                limpio.split(" ").first()
            }
            if (base.isNotEmpty()) {
                nombreUsuario = base.replaceFirstChar { it.uppercase() }
            }
        }
    }

    val mensajeLumi: String
        get() = when {
            rachaPerdida && !checkInCompletadoHoy ->
                "¡No pasa nada, $nombreUsuario! Ayer se nos escapó el registro y la racha volvió a 0, pero hoy es un buen día para empezar de nuevo 💪"
            !checkInCompletadoHoy ->
                "¡Hola $nombreUsuario! Tu racha de $diasRacha días te espera. Completa tu registro de hoy para mantener la llama encendida 🔥"
            animoSeleccionado != null && animoSeleccionado!! <= 2 ->
                "Gracias por registrar cómo te sientes hoy, $nombreUsuario. Recuerda que si necesitas desahogarte, puedes tocar mi foto arriba para charlar 💚"
            else ->
                "¡Racha del día completada, $nombreUsuario! Tu emoción de hoy ya quedó registrada ✨"
        }

    val consejoDelDia: Pair<String, String>
        get() = when {
            animoSeleccionado != null && animoSeleccionado!! <= 2 ->
                "Inhala en 4 segundos, sostén 3 y exhala en 4. Baja el ritmo un momento." to "Calma tu sistema nervioso al instante."
            animoSeleccionado != null && animoSeleccionado!! >= 4 ->
                "Aprovecha esta buena energía para escribir 3 cosas por las que te sientas agradecido hoy." to "Refuerza tu bienestar emocional."
            else ->
                "Tómate 5 minutos para respirar profundamente antes de empezar tu siguiente tarea." to "Reduce el estrés instantáneamente."
        }

    fun seleccionarAnimo(
        animo: Int,
        onIniciarCheckIn: (Int) -> Unit,
        onYaCompletado: () -> Unit
    ) {
        if (checkInCompletadoHoy) {
            onYaCompletado()
            return
        }
        animoSeleccionado = animo
        onIniciarCheckIn(animo)
    }

    // Guarda el ánimo elegido si el usuario entró por el botón "Completar racha de hoy"
    fun fijarAnimoTemporal(animo: Int) {
        if (!checkInCompletadoHoy) {
            animoSeleccionado = animo
        }
    }

    fun completarRachaHoy() {
        if (!checkInCompletadoHoy) {
            checkInCompletadoHoy = true
            rachaPerdida = false
            diasRacha += 1
            diasCompletados[indiceHoy] = true
            if (animoSeleccionado == null) {
                animoSeleccionado = 2 // Neutral si no tocó ninguna carita
            }
        }
    }

    fun toggleDetalleRacha() {
        mostrarDetalleRacha = !mostrarDetalleRacha
    }

    fun simularNuevoDiaParaDemo(): String {
        return when {
            checkInCompletadoHoy -> {
                checkInCompletadoHoy = false
                animoSeleccionado = null
                diasCompletados[indiceHoy] = false
                "Demo: Pasó un día (Check-in pendiente)"
            }
            !rachaPerdida -> {
                rachaPerdida = true
                diasRacha = 0
                for (i in 0 until 14) diasCompletados[i] = false
                "Demo: ¡No hiciste check-in! Racha perdida (0 días)"
            }
            else -> {
                rachaPerdida = false
                diasRacha = DatosDePrueba.DIAS_RACHA
                val previos = diasRacha.coerceIn(0, 8)
                for (i in 0 until 14) {
                    diasCompletados[i] = i in (indiceHoy - previos) until indiceHoy
                }
                "Demo: Racha restaurada a $diasRacha días"
            }
        }
    }
}