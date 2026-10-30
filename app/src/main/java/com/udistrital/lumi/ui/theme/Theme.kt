package com.udistrital.lumi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Por ahora solo hay tema claro, porque los mockups solo tienen modo claro.
// Se quito el "dynamic color" de Android 12 porque cambiaba los colores de la app
// segun el fondo de pantalla del celular y ya no se veia como en Figma.
private val LumiColorScheme = lightColorScheme(
    primary = VerdeLumi,
    onPrimary = Color.White,
    secondary = VerdeSuave,
    background = GrisFondo,
    surface = Color.White,
    onBackground = TextoOscuro,
    onSurface = TextoOscuro
)

@Composable
fun LumiTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LumiColorScheme,
        typography = Typography,
        content = content
    )
}
