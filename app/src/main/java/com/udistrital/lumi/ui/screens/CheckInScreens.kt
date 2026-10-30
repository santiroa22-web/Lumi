package com.udistrital.lumi.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.udistrital.lumi.R
import com.udistrital.lumi.data.DatosDePrueba
import com.udistrital.lumi.ui.components.BotonPrincipal
import com.udistrital.lumi.ui.components.EncabezadoCheckIn
import com.udistrital.lumi.ui.theme.*

// ---------------------------------------------------------------
// Registro diario (check-in) en 3 pasos:
//   1. ¿Cómo te sientes hoy?
//   2. Pausa activa (juego de parejas)
//   3. Tu entorno y vínculos
// Al terminar se muestra la pantalla de racha.
// ---------------------------------------------------------------

// PASO 1
@Composable
fun CheckInAnimoScreen(
    onCerrar: () -> Unit = {},
    onContinuar: () -> Unit = {}
) {
    var seleccion by remember { mutableStateOf<String?>(null) }

    val opciones = listOf(
        "Excelente" to Icons.Default.FavoriteBorder,
        "Tranquilo" to Icons.Default.SentimentSatisfied,
        "Neutral" to Icons.Default.SentimentNeutral,
        "Estresado" to Icons.Default.SentimentDissatisfied,
        "Triste" to Icons.Default.SentimentVeryDissatisfied
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSuave)
            .padding(20.dp)
    ) {
        EncabezadoCheckIn(paso = 1, onCerrar = onCerrar)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // En el mockup hay una foto decorativa; se cambio por la mascota Lumi
            // para no usar imagenes de internet sin licencia
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(VerdeClaro, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = R.drawable.lumi), contentDescription = null, modifier = Modifier.size(120.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("¿Cómo te sientes hoy?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(
                "Tómate un momento para conectar con tus emociones.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            opciones.forEach { (texto, icono) ->
                OpcionLista(texto, icono, seleccion == texto) { seleccion = texto }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(texto = "Continuar", onClick = onContinuar, habilitado = seleccion != null)
    }
}

@Composable
fun OpcionLista(texto: String, icono: ImageVector, seleccionada: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (seleccionada) VerdeClaro else Color.White,
        border = BorderStroke(2.dp, if (seleccionada) VerdeLumi else Color.White),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icono, contentDescription = null, tint = if (seleccionada) VerdeLumi else TextoGris)
            Spacer(modifier = Modifier.width(14.dp))
            Text(texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        }
    }
}

// PASO 2 - Pausa activa: juego de memoria para encontrar las parejas
data class Carta(val id: Int, val icono: ImageVector)

@Composable
fun PausaActivaScreen(
    onCerrar: () -> Unit = {},
    onContinuar: () -> Unit = {}
) {
    // 3 parejas = 6 cartas, se barajan una sola vez
    val cartas = remember {
        val iconos = listOf(Icons.Default.FavoriteBorder, Icons.Default.AutoAwesome, Icons.Default.EmojiEvents)
        (iconos + iconos).mapIndexed { i, icono -> Carta(i, icono) }.shuffled()
    }
    val encontradas = remember { mutableStateListOf<Int>() } // ids de cartas ya emparejadas
    val volteadas = remember { mutableStateListOf<Int>() }   // maximo 2 a la vez
    val juegoTerminado = encontradas.size == cartas.size

    // Cuando hay 2 cartas volteadas se revisa si son pareja
    LaunchedEffect(volteadas.size) {
        if (volteadas.size == 2) {
            val c1 = cartas.first { it.id == volteadas[0] }
            val c2 = cartas.first { it.id == volteadas[1] }
            if (c1.icono == c2.icono) {
                encontradas.addAll(volteadas)
            } else {
                delay(700) // se dejan ver un momento antes de taparlas
            }
            volteadas.clear()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoCalido())
            .padding(20.dp)
    ) {
        EncabezadoCheckIn(paso = 2, titulo = "Pausa Activa", onCerrar = onCerrar)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Entrena tu mente", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(
                "Encuentra las parejas de iconos para completar este ejercicio de atención plena.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Tablero de 2 columnas x 3 filas
            for (fila in 0 until 3) {
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 2) {
                        val carta = cartas[fila * 2 + col]
                        val visible = carta.id in encontradas || carta.id in volteadas
                        CartaMemoria(
                            carta = carta,
                            visible = visible,
                            encontrada = carta.id in encontradas,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (!visible && volteadas.size < 2) volteadas.add(carta.id)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            if (juegoTerminado) {
                Text("¡Muy bien! Completaste la pausa activa 🎉", color = VerdeLumi, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        // Se puede saltar el juego si el usuario no quiere jugar (no es obligatorio)
        BotonPrincipal(texto = if (juegoTerminado) "Continuar" else "Saltar", onClick = onContinuar)
    }
}

// Fondo calido del paso 2 (como en el mockup)
private fun fondoCalido() = androidx.compose.ui.graphics.Brush.verticalGradient(
    listOf(Color(0xFFF3E6DD), Color(0xFFF7F1EC), Color(0xFFF4F6F9))
)

@Composable
fun CartaMemoria(
    carta: Carta,
    visible: Boolean,
    encontrada: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colorFondo by animateColorAsState(
        targetValue = when {
            encontrada -> VerdeSuave
            visible -> VerdeLumi
            else -> Color(0xFF26A69A)
        },
        label = "colorCarta"
    )
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = colorFondo,
        shadowElevation = 2.dp,
        modifier = modifier
            .aspectRatio(1.1f)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (visible) carta.icono else Icons.Default.Psychology,
                contentDescription = null,
                tint = if (visible) Color.White else Color.White.copy(alpha = 0.35f),
                modifier = Modifier.size(44.dp)
            )
        }
    }
}

// PASO 3 - Entorno y vínculos
@Composable
fun VinculosScreen(
    onCerrar: () -> Unit = {},
    onFinalizar: () -> Unit = {}
) {
    // Cada pregunta guarda su respuesta en este mapa
    val respuestas = remember { mutableStateMapOf<String, String>() }

    val preguntas = listOf(
        "¿Cómo estuvo la relación con tu familia hoy?" to listOf("Muy cercana", "Tranquila", "Hubo tensión", "Me sentí incomprendido"),
        "¿Te sentiste acompañado hoy?" to listOf("Sí, apoyado", "Lo normal", "Me sentí solo"),
        "¿Hubo momentos de mucho estrés?" to listOf("No", "Un poco", "Sí, bastante")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSuave)
            .padding(20.dp)
    ) {
        EncabezadoCheckIn(paso = 3, onCerrar = onCerrar)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Tu Entorno & Vínculos 🌿", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text("Control rápido de cómo estuvieron tus relaciones hoy", fontSize = 14.sp, color = TextoGris)
            Spacer(modifier = Modifier.height(16.dp))

            preguntas.forEach { (pregunta, opciones) ->
                Surface(shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(pregunta, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
                        Spacer(modifier = Modifier.height(10.dp))
                        FilasDeChips(
                            opciones = opciones,
                            seleccionada = respuestas[pregunta],
                            onSeleccion = { respuestas[pregunta] = it }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        BotonPrincipal(
            texto = "Finalizar Registro ✨",
            onClick = onFinalizar,
            habilitado = respuestas.size == preguntas.size
        )
    }
}

// Pone los chips de 2 en 2 (FlowRow era experimental y nos dio problemas)
@Composable
fun FilasDeChips(opciones: List<String>, seleccionada: String?, onSeleccion: (String) -> Unit) {
    opciones.chunked(2).forEach { par ->
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            par.forEach { opcion ->
                val activa = opcion == seleccionada
                Surface(
                    shape = RoundedCornerShape(50),
                    color = if (activa) VerdeLumi else Color.White,
                    border = BorderStroke(1.dp, if (activa) VerdeLumi else GrisBoton),
                    modifier = Modifier.clickable { onSeleccion(opcion) }
                ) {
                    Text(
                        opcion,
                        fontSize = 13.sp,
                        color = if (activa) Color.White else TextoOscuro,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

// Pantalla final: racha aumentada
@Composable
fun RachaScreen(
    onVolver: () -> Unit = {}
) {
    val nuevaRacha = DatosDePrueba.DIAS_RACHA + 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoSuave)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Surface(shape = CircleShape, color = VerdeClaro, modifier = Modifier.size(52.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(32.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("¡Racha Aumentada!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(16.dp))

        Surface(shape = RoundedCornerShape(50), color = Color.White, shadowElevation = 2.dp) {
            Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = NaranjaRacha, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("$nuevaRacha", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Spacer(modifier = Modifier.width(6.dp))
                Text("días", fontSize = 14.sp, color = TextoGris)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFE8EAF6), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(id = R.drawable.lumi), contentDescription = null, modifier = Modifier.size(150.dp))
            Icon(
                Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = AmarilloEnergia,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
        Text("RECOMPENSAS GANADAS", fontSize = 12.sp, letterSpacing = 2.sp, color = TextoGris)
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaRecompensa("+1 Día", "Progreso de racha diaria")
        Spacer(modifier = Modifier.height(10.dp))
        TarjetaRecompensa("+100 PTS", "Puntos de bienestar Lumi")

        Spacer(modifier = Modifier.height(28.dp))
        BotonPrincipal(texto = "Volver al inicio", onClick = onVolver)
    }
}

@Composable
fun TarjetaRecompensa(titulo: String, subtitulo: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, VerdeSuave),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Text(subtitulo, fontSize = 13.sp, color = TextoGris)
        }
    }
}
