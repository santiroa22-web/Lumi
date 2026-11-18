package com.udistrital.lumi.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.model.components.iconoAnimo
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.CalendarioViewModel
import com.udistrital.lumi.viewmodel.DiaCalendario
import com.udistrital.lumi.viewmodel.HomeViewModel

@Composable
fun CalendarioScreen(
    viewModel: CalendarioViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    // Se recompone automáticamente cuando cambia el ánimo o la racha en HomeViewModel
    val diasDelMes = viewModel.construirDiasDelMes(
        animoHoyHome = homeViewModel.animoSeleccionado,
        rachaCompletadaHoy = homeViewModel.checkInCompletadoHoy,
        diasRachaActual = homeViewModel.diasRacha
    )
    val diaSeleccionado = diasDelMes.find { it.numero == viewModel.diaSeleccionadoNumero }
        ?: diasDelMes.first()
    val estadisticas = viewModel.calcularEstadisticas(diasDelMes)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Encabezado
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Tu Mes en Lumi", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text("Registro emocional mensual", fontSize = 13.sp, color = TextoGris)
            }
            Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(28.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Selector de Mes con flechas funcionales
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = viewModel.nombreMesActual,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { viewModel.mesAnterior() },
                        enabled = viewModel.indiceMes > 0
                    ) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Mes anterior")
                    }
                    IconButton(
                        onClick = { viewModel.mesSiguiente() },
                        enabled = viewModel.indiceMes < viewModel.nombresMeses.lastIndex
                    ) {
                        Icon(Icons.Default.ChevronRight, contentDescription = "Mes siguiente")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Cabecera días de la semana (L M X J V S D)
                val encabezados = listOf("L", "M", "X", "J", "V", "S", "D")
                Row(modifier = Modifier.fillMaxWidth()) {
                    encabezados.forEach { letra ->
                        Text(
                            text = letra,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoGris,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Cuadrícula completa del mes (celdas vacías iniciales + días del mes en filas de 7)
                val celdas: List<DiaCalendario?> =
                    List(viewModel.offsetPrimerDia) { null } + diasDelMes
                val semanasMes = celdas.chunked(7)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    semanasMes.forEach { fila ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (col in 0 until 7) {
                                val dia = fila.getOrNull(col)
                                if (dia == null) {
                                    Spacer(modifier = Modifier.weight(1f))
                                } else {
                                    CeldaDiaMes(
                                        dia = dia,
                                        seleccionado = dia.numero == diaSeleccionado.numero,
                                        modifier = Modifier.weight(1f),
                                        onClick = { viewModel.seleccionarDia(dia.numero) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Detalle del día seleccionado
        val colorEstadoDetalle = when (diaSeleccionado.animo) {
            0, 1 -> RojoSuave
            in 2..4 -> VerdeLumi
            else -> TextoGris
        }

        Surface(shape = RoundedCornerShape(20.dp), color = VerdeClaro, modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (diaSeleccionado.animo >= 0) iconoAnimo(diaSeleccionado.animo) else Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = colorEstadoDetalle,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${diaSeleccionado.diaSemana.uppercase()} ${diaSeleccionado.numero}" +
                                if (diaSeleccionado.esHoy) " (HOY)" else "",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Text(
                        text = viewModel.obtenerTextoDetalle(diaSeleccionado),
                        fontSize = 13.sp,
                        color = TextoGris
                    )
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.White,
                    border = BorderStroke(1.dp, VerdeSuave)
                ) {
                    Text(
                        text = when {
                            diaSeleccionado.animo >= 0 -> "Registrado"
                            diaSeleccionado.esFuturo -> "Próximo"
                            else -> "Pendiente"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (diaSeleccionado.animo >= 0) VerdeLumi else TextoGris,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Análisis Mensual Funcional
        Text("Análisis Mensual", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Progreso Emocional", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Outlined.Info, contentDescription = null, tint = TextoGris, modifier = Modifier.size(16.dp))
                    }
                    Surface(shape = RoundedCornerShape(50), color = VerdeLumi) {
                        Text(
                            text = estadisticas.etiquetaBalance,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
                Text(
                    text = "Basado en ${estadisticas.totalRegistrados} registros de ${viewModel.nombreMesActual}.",
                    fontSize = 13.sp,
                    color = TextoGris
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gráfica de barras funcional con la frecuencia de cada estado de ánimo (0 a 4)
                val maxConteo = (estadisticas.conteoPorAnimo.maxOrNull() ?: 1).coerceAtLeast(1)
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = VerdeClaro,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        for (nivel in 0..4) {
                            val cantidad = estadisticas.conteoPorAnimo[nivel]
                            val alturaBarra = (12 + (cantidad.toFloat() / maxConteo) * 64).dp
                            val colorBarra = if (nivel <= 1) RojoSuave else VerdeLumi

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$cantidad",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(22.dp)
                                        .height(alturaBarra)
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                        .background(if (cantidad > 0) colorBarra else colorBarra.copy(alpha = 0.25f))
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Icon(
                                    imageVector = iconoAnimo(nivel),
                                    contentDescription = null,
                                    tint = colorBarra,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 4 Tarjetas de estadísticas calculadas en tiempo real
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MiniEstadistica("Promedio", estadisticas.promedioTexto, Icons.Outlined.SentimentSatisfied, Modifier.weight(1f))
                    MiniEstadistica("Racha actual", "${homeViewModel.diasRacha} días", Icons.Default.Bolt, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MiniEstadistica("Frecuente", estadisticas.emocionDominante, Icons.Default.Spa, Modifier.weight(1f))
                    MiniEstadistica("Constancia", estadisticas.porcentajeConstancia, Icons.AutoMirrored.Filled.TrendingUp, Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "\"Cada pequeño paso cuenta para tu paz mental. Lo estás haciendo muy bien.\"",
            fontSize = 13.sp,
            fontStyle = FontStyle.Italic,
            color = TextoGris,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CeldaDiaMes(
    dia: DiaCalendario,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colorCarita = when {
        seleccionado -> Color.White
        dia.animo in 0..1 -> RojoSuave
        dia.animo in 2..4 -> VerdeLumi
        else -> TextoGris.copy(alpha = 0.35f)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = when {
            seleccionado -> VerdeLumi
            dia.esHoy -> VerdeSuave.copy(alpha = 0.55f)
            else -> GrisFondo
        },
        modifier = modifier
            .then(
                if (dia.esHoy && !seleccionado) Modifier.border(1.5.dp, VerdeLumi, RoundedCornerShape(12.dp))
                else Modifier
            )
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${dia.numero}",
                fontSize = 11.sp,
                fontWeight = if (dia.esHoy || seleccionado) FontWeight.Bold else FontWeight.Medium,
                color = when {
                    seleccionado -> Color.White
                    dia.esFuturo -> TextoGris.copy(alpha = 0.5f)
                    else -> TextoOscuro
                }
            )
            Spacer(modifier = Modifier.height(3.dp))
            if (dia.animo >= 0) {
                Icon(
                    imageVector = iconoAnimo(dia.animo),
                    contentDescription = null,
                    tint = colorCarita,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                // Punto sutil para días sin registro o futuros
                Box(
                    modifier = Modifier.size(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(if (seleccionado) Color.White.copy(alpha = 0.6f) else TextoGris.copy(alpha = 0.3f))
                    )
                }
            }
        }
    }
}

@Composable
fun MiniEstadistica(titulo: String, valor: String, icono: ImageVector, modifier: Modifier = Modifier) {
    Surface(shape = RoundedCornerShape(16.dp), color = GrisFondo, modifier = modifier) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icono, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(titulo, fontSize = 11.sp, color = TextoGris)
                Text(valor, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
            }
        }
    }
}