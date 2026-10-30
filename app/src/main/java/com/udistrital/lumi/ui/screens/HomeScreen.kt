package com.udistrital.lumi.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Lightbulb
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
import com.udistrital.lumi.R
import com.udistrital.lumi.data.DatosDePrueba
import com.udistrital.lumi.ui.components.ChipRacha
import com.udistrital.lumi.ui.components.SelectorAnimo
import com.udistrital.lumi.ui.theme.*

@Composable
fun HomeScreen(
    onAnimoClick: (Int) -> Unit = {},
    onAvatarClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var animoSeleccionado by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Campana y avatar de Lumi (el avatar abre el chat)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                Toast.makeText(context, "No tienes notificaciones nuevas", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Outlined.Notifications, contentDescription = "Notificaciones", tint = TextoOscuro)
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

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Hola, ${DatosDePrueba.NOMBRE_USUARIO} 👋",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
                Text("Es un buen día para cuidarte.", fontSize = 14.sp, color = TextoGris)
            }
            ChipRacha(dias = DatosDePrueba.DIAS_RACHA)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta "¿Cómo te sientes hoy?" -> al tocar una carita empieza el registro diario
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
                Text("¿Cómo te sientes hoy?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                Spacer(modifier = Modifier.height(16.dp))
                SelectorAnimo(
                    seleccionado = animoSeleccionado,
                    onSeleccion = {
                        animoSeleccionado = it
                        onAnimoClick(it)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Consejo de hoy", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
            Text(
                "VER MÁS",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeLumi,
                modifier = Modifier.clickable {
                    Toast.makeText(context, "Próximamente más consejos", Toast.LENGTH_SHORT).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

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
                        "Tómate 5 minutos para respirar profundamente antes de empezar tu siguiente tarea.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F5E50),
                        lineHeight = 21.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Reduce el estrés instantáneamente.", fontSize = 13.sp, color = VerdeLumi)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AmarilloEnergia, modifier = Modifier.size(48.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Actividades rápidas", fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        Spacer(modifier = Modifier.height(12.dp))

        TarjetaActividad("Meditación Guiada", "10 min", Icons.Default.SelfImprovement) {
            Toast.makeText(context, "Meditación guiada: próximamente", Toast.LENGTH_SHORT).show()
        }
        Spacer(modifier = Modifier.height(12.dp))
        TarjetaActividad("Diario de Gratitud", "5 min", Icons.Default.EditNote) {
            Toast.makeText(context, "Diario de gratitud: próximamente", Toast.LENGTH_SHORT).show()
        }

        Spacer(modifier = Modifier.height(24.dp))
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
