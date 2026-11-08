package com.udistrital.lumi.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock // <-- Importación del ícono añadida
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.model.components.BotonPrincipal
import com.udistrital.lumi.model.components.CampoTexto
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.RecuperacionViewModel

@Composable
fun CambiarContrasenaScreen(
    viewModel: RecuperacionViewModel,
    onBackClick: () -> Unit,
    onNavigateHome: () -> Unit
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
                text = "Cambiar Contraseña",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Por favor, crea tu nueva contraseña para acceder.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            CampoTexto(
                etiqueta = "NUEVA CONTRASEÑA",
                valor = viewModel.nuevaContrasena,
                onValorChange = { viewModel.nuevaContrasena = it },
                placeholder = "••••••••",
                icono = Icons.Default.Lock, // <-- Parámetro corregido
                esContrasena = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                etiqueta = "CONFIRMAR NUEVA CONTRASEÑA",
                valor = viewModel.confirmarContrasena,
                onValorChange = { viewModel.confirmarContrasena = it },
                placeholder = "••••••••",
                icono = Icons.Default.Lock, // <-- Parámetro corregido
                esContrasena = true
            )

            Spacer(modifier = Modifier.weight(1f))

            BotonPrincipal(
                texto = "Actualizar Contraseña",
                onClick = { viewModel.actualizarContrasena(onNavigateHome) },
                habilitado = viewModel.nuevaContrasena.isNotEmpty() && viewModel.nuevaContrasena == viewModel.confirmarContrasena
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Al actualizar, aceptas nuestros Términos y Privacidad",
                fontSize = 11.sp,
                color = TextoGris,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}