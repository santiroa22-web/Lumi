package com.udistrital.lumi.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.R
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.FaseRespiracion
import com.udistrital.lumi.viewmodel.RespiracionViewModel

@Composable
fun RespiracionScreen(
    onBackClick: () -> Unit = {},
    viewModel: RespiracionViewModel = viewModel()
) {
    // Animación fluida del círculo según la fase (crece en 4s al inhalar, baja en 4s al exhalar)
    val duracionAnimacion = when (viewModel.faseActual) {
        FaseRespiracion.INHALA -> 3800
        FaseRespiracion.EXHALA -> 3800
        else -> 600
    }

    val escalaCirculo by animateFloatAsState(
        targetValue = viewModel.faseActual.escalaObjetivo,
        animationSpec = tween(durationMillis = duracionAnimacion, easing = FastOutSlowInEasing),
        label = "escalaRespiracion"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado con botón regresar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    viewModel.detenerEjercicio()
                    onBackClick()
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextoOscuro)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Pausa de Respiración",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Text(
                    text = if (viewModel.enEjecucion) "Ciclo ${viewModel.cicloActual} de ${viewModel.totalCiclos}" else "Espacio de calma con Lumi",
                    fontSize = 13.sp,
                    color = TextoGris
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Barra de progreso sutil
        LinearProgressIndicator(
            progress = { viewModel.progresoTotal },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50)),
            color = VerdeLumi,
            trackColor = VerdeSuave
        )

        Spacer(modifier = Modifier.weight(1f))

        // Instrucción de fase actual
        Text(
            text = viewModel.faseActual.titulo,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextoOscuro,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = viewModel.faseActual.subtitulo,
            fontSize = 15.sp,
            color = TextoGris,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Círculo animado de respiración con cronómetro central
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(260.dp)
        ) {
            // Halo exterior suave
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .scale(escalaCirculo)
                    .clip(CircleShape)
                    .background(VerdeLumi.copy(alpha = 0.15f))
            )
            // Círculo principal con degradado
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(200.dp)
                    .scale(escalaCirculo)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(VerdeLumi, Color(0xFF1B8A75))
                        )
                    )
            ) {
                if (viewModel.enEjecucion) {
                    Text(
                        text = "${viewModel.segundosRestantes}s",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.lumi),
                        contentDescription = "Lumi calma",
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Tarjeta motivacional cuando termina la pausa de respiración
        AnimatedVisibility(
            visible = viewModel.faseActual == FaseRespiracion.COMPLETADO,
            enter = fadeIn() + scaleIn()
        ) {
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = VerdeSuave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lumi),
                        contentDescription = "Lumi",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Mensaje de Lumi",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = VerdeLumi
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = viewModel.mensajeFinalElegido,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0F5E50),
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botones de control inferiores
        when {
            viewModel.faseActual == FaseRespiracion.COMPLETADO -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.iniciarEjercicio() },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, tint = VerdeLumi)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Repetir", color = VerdeLumi, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            viewModel.detenerEjercicio()
                            onBackClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeLumi),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Volver al inicio", fontWeight = FontWeight.Bold)
                    }
                }
            }
            viewModel.enEjecucion -> {
                OutlinedButton(
                    onClick = { viewModel.detenerEjercicio() },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Detener pausa", color = TextoGris, fontWeight = FontWeight.SemiBold)
                }
            }
            else -> {
                Button(
                    onClick = { viewModel.iniciarEjercicio() },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeLumi),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Comenzar respiración", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}