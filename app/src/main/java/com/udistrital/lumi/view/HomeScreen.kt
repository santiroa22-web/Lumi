package com.udistrital.lumi.view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.R
import com.udistrital.lumi.model.components.ChipRacha
import com.udistrital.lumi.model.components.SelectorAnimo
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onAnimoClick: (Int) -> Unit = {},
    onAvatarClick: () -> Unit = {},
    onCompletarRachaClick: () -> Unit = {},
    onRespiracionClick: () -> Unit = {},
    onDiarioClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    var mostrarRecordatorios by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Barra superior: Campana y avatar de Lumi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                IconButton(onClick = { mostrarRecordatorios = true }) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Notificaciones", tint = TextoOscuro)
                }
                if (!viewModel.checkInCompletadoHoy) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-8).dp, y = 8.dp)
                            .clip(CircleShape)
                            .background(NaranjaRacha)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.lumi),
                contentDescription = "Hablar con Lumi",
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable { onAvatarClick() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bienvenida solo con el nombre (sin mano) y Chip de Racha
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hola, ${viewModel.nombreUsuario}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Text("Es un buen día para cuidarte.", fontSize = 14.sp, color = TextoGris)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .combinedClickable(
                        onClick = { viewModel.toggleDetalleRacha() },
                        onLongClick = {
                            val msg = viewModel.simularNuevoDiaParaDemo()
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    )
            ) {
                ChipRacha(dias = viewModel.diasRacha)
            }
        }

        // Tarjeta de Racha de 2 semanas (14 días: 2 filas de 7)
        AnimatedVisibility(visible = viewModel.mostrarDetalleRacha) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = if (viewModel.checkInCompletadoHoy) NaranjaRacha else TextoGris,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (viewModel.checkInCompletadoHoy) "¡Racha activa hoy!" else "Racha pendiente hoy",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                        }
                        Text(
                            text = "${viewModel.diasRacha} días",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = NaranjaRacha
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dos semanas: Fila 1 (Semana pasada) y Fila 2 (Esta semana)
                    val semanas = (0 until 14).chunked(7)
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        semanas.forEach { indicesSemana ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                indicesSemana.forEach { index ->
                                    val completado = viewModel.diasCompletados[index]
                                    val esHoy = index == viewModel.indiceHoy
                                    val letraDia = viewModel.etiquetasDias[index]

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    when {
                                                        completado -> NaranjaRacha
                                                        esHoy -> NaranjaRacha.copy(alpha = 0.15f)
                                                        else -> GrisFondo
                                                    }
                                                )
                                                .then(
                                                    if (esHoy && !completado) Modifier.border(1.5.dp, NaranjaRacha, CircleShape)
                                                    else Modifier
                                                )
                                        ) {
                                            if (completado) {
                                                Icon(
                                                    Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            } else {
                                                Text(
                                                    text = letraDia,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (esHoy) NaranjaRacha else TextoGris
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = if (esHoy) "Hoy" else letraDia,
                                            fontSize = 10.sp,
                                            fontWeight = if (esHoy) FontWeight.Bold else FontWeight.Normal,
                                            color = if (esHoy) TextoOscuro else TextoGris
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Banner protagonista de Lumi con botón para completar la racha
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.5.dp,
                    if (viewModel.checkInCompletadoHoy) VerdeLumi.copy(alpha = 0.3f) else NaranjaRacha.copy(alpha = 0.4f),
                    RoundedCornerShape(22.dp)
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.lumi),
                        contentDescription = "Lumi",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Lumi te recuerda:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (viewModel.checkInCompletadoHoy) VerdeLumi else NaranjaRacha
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = viewModel.mensajeLumi,
                            fontSize = 14.sp,
                            color = TextoOscuro,
                            lineHeight = 19.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        if (!viewModel.checkInCompletadoHoy) {
                            onCompletarRachaClick()
                        } else {
                            Toast.makeText(context, "¡Ya aseguraste tu racha de hoy! 🔥", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (viewModel.checkInCompletadoHoy) VerdeSuave else NaranjaRacha,
                        contentColor = if (viewModel.checkInCompletadoHoy) VerdeLumi else Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Icon(
                        imageVector = if (viewModel.checkInCompletadoHoy) Icons.Default.CheckCircle else Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (viewModel.checkInCompletadoHoy) "¡Racha de hoy completada!" else "Completar racha de hoy",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tarjeta "¿Cómo te sientes hoy?"
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (viewModel.checkInCompletadoHoy) "Tu estado de hoy" else "¿Cómo te sientes hoy?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    if (viewModel.checkInCompletadoHoy) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = "Fijado",
                            tint = VerdeLumi,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SelectorAnimo(
                    seleccionado = viewModel.animoSeleccionado,
                    onSeleccion = { animo ->
                        viewModel.seleccionarAnimo(
                            animo = animo,
                            onIniciarCheckIn = onAnimoClick,
                            onYaCompletado = {
                                Toast.makeText(
                                    context,
                                    "Ya registraste tu emoción de hoy 💚",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Consejo de hoy
        Text("Consejo de hoy", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(10.dp))

        val (tituloConsejo, subtituloConsejo) = viewModel.consejoDelDia
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = VerdeSuave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("MINDFULNESS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = VerdeLumi)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tituloConsejo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F5E50),
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(subtituloConsejo, fontSize = 13.sp, color = VerdeLumi)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AmarilloEnergia, modifier = Modifier.size(46.dp))
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Actividades rápidas
        Text("Actividades rápidas", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(10.dp))

        TarjetaActividad("Pausa de Respiración", "Círculo guiado • Calma rápida", Icons.Default.SelfImprovement) {
            onRespiracionClick()
        }
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaActividad("Diario de Gratitud", "Bloc de notas • Tus apuntes", Icons.Default.EditNote) {
            onDiarioClick()
        }

        Spacer(modifier = Modifier.height(28.dp))
    }

    // Modal de Recordatorios
    if (mostrarRecordatorios) {
        var recordatorioActivo by remember { mutableStateOf(true) }
        AlertDialog(
            onDismissRequest = { mostrarRecordatorios = false },
            title = { Text("Recordatorios de Lumi 🔔", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        if (!viewModel.checkInCompletadoHoy) "• Aún no has completado tu racha de hoy."
                        else "• ¡Racha de hoy completada con éxito!",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Aviso diario (8:00 PM)", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Switch(checked = recordatorioActivo, onCheckedChange = { recordatorioActivo = it })
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarRecordatorios = false }) {
                    Text("Listo", color = VerdeLumi, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun TarjetaActividad(
    titulo: String,
    duracion: String,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = RoundedCornerShape(12.dp), color = VerdeClaro, modifier = Modifier.size(44.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icono, contentDescription = null, tint = NaranjaRacha)
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
                Text(duracion, fontSize = 13.sp, color = TextoGris)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextoGris)
        }
    }
}