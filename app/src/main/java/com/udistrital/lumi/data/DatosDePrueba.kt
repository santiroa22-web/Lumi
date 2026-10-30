package com.udistrital.lumi.data

// Datos quemados para mostrar las pantallas mientras no tenemos el backend.
// Cuando este la API REST (Node.js + PostgreSQL) estos datos se van a pedir al servidor.

data class DiaSemana(
    val nombre: String,
    val numero: Int,
    val animo: Int // 0 = mal, 1 = bajo, 2 = neutral, 3 = bien, 4 = genial, -1 = sin registro
)

data class MensajeChat(
    val texto: String,
    val esDeLumi: Boolean,
    val hora: String
)

object DatosDePrueba {

    const val NOMBRE_USUARIO = "Usuario"
    const val DIAS_RACHA = 5

    val semana = listOf(
        DiaSemana("LUN", 30, 3),
        DiaSemana("MAR", 31, 3),
        DiaSemana("MIÉ", 1, 2),
        DiaSemana("JUE", 2, 3),
        DiaSemana("VIE", 3, 0),
        DiaSemana("SÁB", 4, 3),
        DiaSemana("DOM", 5, -1)
    )

    val opcionesAnimo = listOf("Mal", "Bajo", "Neutral", "Bien", "Genial")

    val mensajesIniciales = listOf(
        MensajeChat("¡Hola! Estoy aquí para escucharte. ¿Cómo va tu día?", true, "10:00 AM"),
        MensajeChat("Me he sentido un poco abrumado con los estudios...", false, "10:02 AM")
    )

    // Respuestas de ejemplo de Lumi (luego vendran del backend)
    val respuestasLumi = listOf(
        "Gracias por contármelo. ¿Quieres que hagamos juntos un ejercicio de respiración?",
        "Es normal sentirse así a veces. ¿Qué crees que te ayudaría hoy?",
        "Te escucho. Recuerda que también puedes hablar con alguien de confianza."
    )
}
