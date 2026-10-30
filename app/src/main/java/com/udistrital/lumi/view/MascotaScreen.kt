package com.udistrital.lumi.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.R
import com.udistrital.lumi.ui.components.BarraEstadistica
import com.udistrital.lumi.ui.theme.*
import com.udistrital.lumi.viewmodel.MascotaViewModel // Importación correcta en singular

@Composable
fun MascotaScreen(
    onHablarClick: () -> Unit = {},
    viewModel: MascotaViewModel = viewModel() // Casing corregido: MascotaViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text("Tu Compañero Lumi", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
        Text(
            "«Siempre aquí para escucharte y crecer contigo»",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            color = TextoGris,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.lumi),
                contentDescription = "Mascota Lumi",
                modifier = Modifier.size(200.dp)
            )
            Surface(
                shape = RoundedCornerShape(50),
                color = VerdeClaro,
                border = BorderStroke(1.dp, VerdeSuave),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = VerdeLumi,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = viewModel.estado,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VerdeLumi
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BarraEstadistica("nutrición", viewModel.nutricion, Icons.Default.Restaurant, VerdeLumi)
                BarraEstadistica("felicidad", viewModel.felicidad, Icons.Default.FavoriteBorder, RojoSuave)
                BarraEstadistica("energía", viewModel.energia, Icons.Default.Bolt, AmarilloEnergia)
                BarraEstadistica("bienestar", viewModel.bienestar, Icons.Default.Spa, Color(0xFF7986CB))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            BotonMascota(
                texto = "ALIMENTAR",
                icono = Icons.Default.Restaurant,
                relleno = true,
                modifier = Modifier.weight(1f)
            ) {
                viewModel.alimentar()
            }
            BotonMascota(
                texto = "JUGAR",
                icono = Icons.Default.AutoAwesome,
                relleno = false,
                modifier = Modifier.weight(1f)
            ) {
                viewModel.jugar()
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            BotonMascota(
                texto = "PASEAR",
                icono = Icons.AutoMirrored.Filled.DirectionsWalk,
                relleno = false,
                modifier = Modifier.weight(1f)
            ) {
                viewModel.pasear()
            }
            BotonMascota(
                texto = "HABLAR",
                icono = Icons.Default.ChatBubbleOutline,
                relleno = false,
                modifier = Modifier.weight(1f)
            ) {
                onHablarClick()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun BotonMascota(
    texto: String,
    icono: ImageVector,
    relleno: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (relleno) {
        Button(
            onClick = onClick,
            modifier = modifier.height(48.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = VerdeLumi)
        ) {
            Icon(icono, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(texto, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(48.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, VerdeSuave),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = VerdeLumi)
        ) {
            Icon(icono, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(texto, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}