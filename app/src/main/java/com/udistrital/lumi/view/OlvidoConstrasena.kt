package com.udistrital.lumi.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.model.components.BotonPrincipal
import com.udistrital.lumi.model.components.CampoTexto
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.RecuperacionViewModel

@Composable
fun OlvidoContrasenaScreen(
    viewModel: RecuperacionViewModel,
    onBackClick: () -> Unit,
    onNavigateNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(FondoDegradado).padding(28.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                modifier = Modifier.size(28.dp).clickable { onBackClick() },
                tint = TextoOscuro
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Recuperar\nCuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ingresa tu correo electrónico y te enviaremos un enlace seguro para restablecer tu contraseña.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Surface(shape = CircleShape, color = VerdeLumi, modifier = Modifier.size(76.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            CampoTexto(
                etiqueta = "CORREO ELECTRÓNICO",
                valor = viewModel.correo,
                onValorChange = { viewModel.correo = it },
                placeholder = "correo@ejemplo.com",
                icono = Icons.Default.Email,
                tipoTeclado = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(24.dp))

            BotonPrincipal(
                texto = "Enviar Enlace",
                onClick = {
                    viewModel.enviarEnlaceRecuperacion(
                        onExito = onNavigateNext,
                        onError = { mensajeError ->
                            // Manejo del error en caso de que falle el envío del correo
                            println("Error al enviar recuperación: $mensajeError")
                        }
                    )
                },
                habilitado = viewModel.correo.contains("@")
            )
        }
    }
}