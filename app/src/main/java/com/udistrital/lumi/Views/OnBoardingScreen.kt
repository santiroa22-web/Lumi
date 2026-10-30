package com.udistrital.lumi.views.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.R

@Composable
fun OnboardingScreen(
    onBackClick: () -> Unit = {},
    onFinishOnboarding: () -> Unit = {}
) {
    BackHandler {
        onBackClick()
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF4F7FB),
            Color(0xFFFDF8F5),
            Color(0xFFF1F3F9)
        )
    )

    val colorPrimario = Color(0xFF60C5B1)
    val colorIconoFondo = Color(0xFFE2F6F3)
    val colorTextoOscuro = Color(0xFF212529)

    var selectedOcupacion by remember { mutableStateOf<String?>(null) }
    var selectedEntorno by remember { mutableStateOf<String?>(null) }
    var selectedCuidado by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(bottom = 90.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Bienvenid@",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "¡Vamos a conocerte mejor! ",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mascota Lumi sin fondo + Globo de diálogo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lumi),
                    contentDescription = "Mascota Lumi",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Soy Lumi! Para acompañarte mejor, cuéntame un poco sobre ti",
                        fontSize = 13.sp,
                        color = Color(0xFF555555),
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ¿A qué te dedicas actualmente?
            Text(
                text = "¿A qué te dedicas actualmente?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OptionGridCard(
                    title = "Estudio",
                    icon = Icons.Default.School,
                    isSelected = selectedOcupacion == "Estudio",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedOcupacion = "Estudio" }

                OptionGridCard(
                    title = "Trabajo",
                    icon = Icons.Default.Work,
                    isSelected = selectedOcupacion == "Trabajo",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedOcupacion = "Trabajo" }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OptionGridCard(
                    title = "Estudio y\nTrabajo",
                    icon = Icons.Default.Bolt,
                    isSelected = selectedOcupacion == "Estudio y Trabajo",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedOcupacion = "Estudio y Trabajo" }

                OptionGridCard(
                    title = "Otro",
                    icon = Icons.Default.AutoAwesome,
                    isSelected = selectedOcupacion == "Otro",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedOcupacion = "Otro" }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ¿Cómo es tu entorno cercano?
            Text(
                text = "¿Cómo es tu entorno cercano?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OptionGridCard(
                    title = "Tengo pareja",
                    icon = Icons.Default.FavoriteBorder,
                    isSelected = selectedEntorno == "Tengo pareja",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedEntorno = "Tengo pareja" }

                OptionGridCard(
                    title = "Vivo con familia",
                    icon = Icons.Default.PeopleOutline,
                    isSelected = selectedEntorno == "Vivo con familia",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedEntorno = "Vivo con familia" }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OptionGridCard(
                    title = "Vivo solo/a",
                    icon = Icons.Default.Home,
                    isSelected = selectedEntorno == "Vivo solo/a",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedEntorno = "Vivo solo/a" }

                OptionGridCard(
                    title = "Tengo\nmascotas",
                    icon = Icons.Default.Pets,
                    isSelected = selectedEntorno == "Tengo mascotas",
                    modifier = Modifier.weight(1f),
                    colorPrimario = colorPrimario,
                    colorFondoIcono = colorIconoFondo
                ) { selectedEntorno = "Tengo mascotas" }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ¿Qué te gustaría cuidar más?
            Text(
                text = "¿Qué te gustaría cuidar más?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )
            Spacer(modifier = Modifier.height(14.dp))

            OptionRowCard(
                title = "Manejo del estrés",
                icon = Icons.Default.Air,
                isSelected = selectedCuidado == "Manejo del estrés",
                colorPrimario = colorPrimario,
                colorFondoIcono = colorIconoFondo
            ) { selectedCuidado = "Manejo del estrés" }

            Spacer(modifier = Modifier.height(12.dp))

            OptionRowCard(
                title = "Relaciones",
                icon = Icons.Default.PeopleOutline,
                isSelected = selectedCuidado == "Relaciones",
                colorPrimario = colorPrimario,
                colorFondoIcono = colorIconoFondo
            ) { selectedCuidado = "Relaciones" }

            Spacer(modifier = Modifier.height(12.dp))

            OptionRowCard(
                title = "Motivación",
                icon = Icons.Default.Bolt,
                isSelected = selectedCuidado == "Motivación",
                colorPrimario = colorPrimario,
                colorFondoIcono = colorIconoFondo
            ) { selectedCuidado = "Motivación" }

            Spacer(modifier = Modifier.height(12.dp))

            OptionRowCard(
                title = "Sueño",
                icon = Icons.Default.NightsStay,
                isSelected = selectedCuidado == "Sueño",
                colorPrimario = colorPrimario,
                colorFondoIcono = colorIconoFondo
            ) { selectedCuidado = "Sueño" }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Botón fijo abajo
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.85f))
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Button(
                onClick = onFinishOnboarding,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE9ECEF),
                    contentColor = Color(0xFF888888)
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text("Comenzar la experiencia ", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun OptionGridCard(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    colorPrimario: Color,
    colorFondoIcono: Color,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(if (isSelected) colorPrimario else Color.Transparent, label = "borderColor")

    Surface(
        modifier = modifier
            .height(76.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(2.dp, if (isSelected) colorPrimario else Color.White),
        shadowElevation = if (isSelected) 4.dp else 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) colorPrimario else colorFondoIcono,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else colorPrimario,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2B2B),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun OptionRowCard(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    colorPrimario: Color,
    colorFondoIcono: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(2.dp, if (isSelected) colorPrimario else Color.White),
        shadowElevation = if (isSelected) 4.dp else 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) colorPrimario else colorFondoIcono,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else colorPrimario,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2B2B2B)
            )
        }
    }
}