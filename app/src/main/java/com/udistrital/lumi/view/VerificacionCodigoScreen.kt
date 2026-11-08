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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.model.components.BotonPrincipal
import com.udistrital.lumi.model.components.CampoTexto
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.RecuperacionViewModel

@Composable
fun VerificacionCodigoScreen(
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
                text = "Código de Verificación",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hemos enviado un código a su correo electrónico. Por favor, ingréselo a continuación.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            CampoTexto(
                etiqueta = "CÓDIGO DE 4 DÍGITOS",
                valor = viewModel.codigoVerificacion,
                onValorChange = { if (it.length <= 4) viewModel.codigoVerificacion = it },
                placeholder = "1234",
                icono = Icons.Default.Lock, // <-- Parámetro corregido
                tipoTeclado = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(24.dp))

            val textoReenvio = if (viewModel.tiempoRestante > 0) {
                "Reenviar código (0:\${viewModel.tiempoRestante.toString().padStart(2, '0')})"
            } else {
                "Reenviar código ahora"
            }

            Text(
                text = "¿No has recibido el código?\nReenviar texto",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            BotonPrincipal(
                texto = "Verificar Código",
                onClick = { viewModel.validarCodigo(onNavigateNext) },
                habilitado = viewModel.codigoVerificacion.length == 4
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}