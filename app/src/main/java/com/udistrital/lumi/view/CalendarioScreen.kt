package com.udistrital.lumi.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.data.DiaSemana
import com.udistrital.lumi.ui.components.iconoAnimo
import com.udistrital.lumi.ui.theme.*
import com.udistrital.lumi.viewmodel.CalendarioViewModel

@Composable
fun CalendarioScreen(
    viewModel: CalendarioViewModel = viewModel()
) {
    val diaSeleccionado = viewModel.diaSeleccionado
    val semana = viewModel.semana

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Tu Semana en Lumi", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Text("Agosto 30 - Septiembre 5, 2026", fontSize = 13.sp, color = TextoGris)
            }
            Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = VerdeLumi)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Estado de Ánimo", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro, modifier = Modifier.weight(1f))
            IconButton(onClick = { }) { Icon(Icons.Default.ChevronLeft, contentDescription = "Semana anterior") }
            IconButton(onClick = { }) { Icon(Icons.Default.ChevronRight, contentDescription = "Semana siguiente") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Primera fila: lunes a jueves, segunda fila: viernes a domingo
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            for (i in 0..3) {
                TarjetaDia(
                    dia = semana[i],
                    seleccionado = semana[i] == diaSeleccionado,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.seleccionarDia(semana[i])
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            for (i in 4..6) {
                TarjetaDia(
                    dia = semana[i],
                    seleccionado = semana[i] == diaSeleccionado,
                    modifier = Modifier.weight(1f)
                ) {
                    viewModel.seleccionarDia(semana[i])
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Detalle del día seleccionado
        Surface(shape = RoundedCornerShape(20.dp), color = VerdeClaro, modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WbSunny, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("DETALLE DEL ${diaSeleccionado.nombre}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Text(viewModel.obtenerTextoDetalle(diaSeleccionado.animo), fontSize = 13.sp, color = TextoGris)
                }
                Surface(shape = RoundedCornerShape(50), color = Color.White, border = BorderStroke(1.dp, VerdeSuave)) {
                    Text(
                        text = if (diaSeleccionado.animo >= 0) "Completado" else "Pendiente",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeLumi,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text("Análisis Semanal", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(12.dp))

        Surface(shape = RoundedCornerShape(24.dp), color = Color.White, shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Progreso Emocional", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.Outlined.Info, contentDescription = null, tint = TextoGris, modifier = Modifier.size(16.dp))
                }
                Text("Tu equilibrio está mejorando gradualmente.", fontSize = 13.sp, color = TextoGris)

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(VerdeClaro, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Spa, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(72.dp))
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = VerdeLumi,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    ) {
                        Text(
                            "Semana Equilibrada",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MiniEstadistica("Promedio", "positivo", Icons.Outlined.SentimentSatisfied, Modifier.weight(1f))
                    MiniEstadistica("Racha", "${viewModel.diasRacha} días", Icons.Default.Bolt, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MiniEstadistica("Emoción", "gratitud", Icons.Default.Spa, Modifier.weight(1f))
                    MiniEstadistica("Mejora", "+12%", Icons.AutoMirrored.Filled.TrendingUp, Modifier.weight(1f))
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
fun TarjetaDia(
    dia: DiaSemana,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colorCarita = when {
        seleccionado -> Color.White
        dia.animo in 0..1 -> RojoSuave
        else -> VerdeLumi
    }
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (seleccionado) VerdeLumi else Color.White,
        shadowElevation = 1.dp,
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(dia.nombre, fontSize = 12.sp, color = if (seleccionado) Color.White else TextoGris)
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = CircleShape,
                color = if (seleccionado) Color.White.copy(alpha = 0.25f) else GrisFondo,
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "${dia.numero}",
                        fontWeight = FontWeight.Bold,
                        color = if (seleccionado) Color.White else TextoOscuro
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Icon(iconoAnimo(dia.animo), contentDescription = null, tint = colorCarita, modifier = Modifier.size(22.dp))
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
                Text(titulo, fontSize = 12.sp, color = TextoGris)
                Text(valor, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
            }
        }
    }
}