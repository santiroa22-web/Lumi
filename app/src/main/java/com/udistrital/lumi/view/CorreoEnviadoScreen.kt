package com.udistrital.lumi.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.model.components.BotonPrincipal
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.RecuperacionViewModel

@Composable
fun CorreoEnviadoScreen(
    viewModel: RecuperacionViewModel,
    onAceptarClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(FondoDegradado).padding(28.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Círculo verde con un chulo (check) de éxito
            Surface(shape = CircleShape, color = VerdeLumi, modifier = Modifier.size(90.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Check, contentDescription = "Éxito", tint = Color.White, modifier = Modifier.size(50.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "¡Enlace Enviado!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hemos enviado las instrucciones para restablecer tu contraseña a:\n\n${viewModel.correo}\n\nPor favor, revisa tu bandeja de entrada o la carpeta de Spam.",
                fontSize = 15.sp,
                color = TextoGris,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            BotonPrincipal(
                texto = "Aceptar",
                onClick = onAceptarClick,
                habilitado = true
            )
        }
    }
}