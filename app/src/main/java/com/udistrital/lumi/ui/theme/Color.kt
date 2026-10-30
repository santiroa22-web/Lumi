package com.udistrital.lumi.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Colores de Lumi (sacados de los mockups de Figma)
// Antes cada pantalla tenia su propio verde (#00C49A y #60C5B1), ahora todas usan los mismos
val VerdeLumi = Color(0xFF1EBFA0)
val VerdeClaro = Color(0xFFE2F6F3)
val VerdeSuave = Color(0xFFC9F2E8)
val TextoOscuro = Color(0xFF1F2328)
val TextoGris = Color(0xFF757575)
val GrisFondo = Color(0xFFF4F6F9)
val GrisBoton = Color(0xFFE9ECEF)
val RojoSuave = Color(0xFFE57373)
val NaranjaRacha = Color(0xFFFF7A45)
val AmarilloEnergia = Color(0xFFFFC94D)

// Fondo degradado de login y registro
val FondoDegradado = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFEBF4FF),
        Color(0xFFFDE8E8),
        Color(0xFFE4E5F1)
    )
)

// Fondo mas suave para onboarding y check-in
val FondoSuave = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF4F7FB),
        Color(0xFFFDF8F5),
        Color(0xFFF1F3F9)
    )
)

// Colores que venian por defecto con el proyecto (no se usan, se dejan por si acaso)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
