package com.udistrital.lumi.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.ui.components.BotonPrincipal
import com.udistrital.lumi.ui.components.CampoTexto
import com.udistrital.lumi.ui.theme.*

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    // Estado de los campos (antes estaban fijos en "" y no se podia escribir)
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    // Validacion sencilla mientras no hay backend: correo con @ y clave de 6 o mas caracteres
    val datosValidos = correo.contains("@") && contrasena.length >= 6

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoDegradado)
            .padding(horizontal = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            // Logo de Lumi (en el mockup esta el circulo verde con la estrella, antes faltaba)
            Surface(
                shape = CircleShape,
                color = VerdeLumi,
                modifier = Modifier.size(76.dp),
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = "Logo Lumi",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bienvenido a Lumi",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Tu espacio seguro para la paz mental\ny el bienestar diario.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            CampoTexto(
                etiqueta = "CORREO ELECTRÓNICO",
                valor = correo,
                onValorChange = { correo = it },
                placeholder = "nombre@ejemplo.com",
                icono = Icons.Default.Email,
                tipoTeclado = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                etiqueta = "CONTRASEÑA",
                valor = contrasena,
                onValorChange = { contrasena = it },
                placeholder = "••••••••",
                icono = Icons.Default.Lock,
                esContrasena = true,
                accionDerecha = {
                    Text(
                        text = "¿Olvidaste la clave?",
                        fontSize = 12.sp,
                        color = VerdeLumi,
                        fontWeight = FontWeight.Medium
                    )
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Solo se habilita si el correo y la clave tienen formato valido
            BotonPrincipal(
                texto = "Iniciar Sesión",
                onClick = onLoginClick,
                habilitado = datosValidos
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "o continúa con", fontSize = 13.sp, color = TextoGris)

            // Se quitaron los botones de Google/Facebook/Apple que no estaban en el mockup.
            // La arquitectura usa autenticacion propia con JWT, entonces no aplican por ahora.

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = VerdeLumi),
                border = BorderStroke(1.dp, VerdeLumi)
            ) {
                Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Al entrar, aceptas nuestros Términos y Privacidad",
                fontSize = 11.sp,
                color = TextoGris,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
