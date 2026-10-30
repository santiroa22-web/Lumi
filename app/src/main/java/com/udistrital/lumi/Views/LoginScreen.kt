package com.udistrital.lumi.views.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {}
) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEBF4FF),
            Color(0xFFFDE8E8),
            Color(0xFFE4E5F1)
        )
    )

    val colorPrimario = Color(0xFF00C49A)
    val colorTextoOscuro = Color(0xFF1A1A1A)
    val colorTextoGris = Color(0xFF757575)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))



            Spacer(modifier = Modifier.height(80.dp))

            // Textos de Bienvenida
            Text(
                text = "Bienvenido a Lumi",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Tu espacio seguro para la paz mental\ny el bienestar diario.",
                fontSize = 14.sp,
                color = colorTextoGris,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(  100.dp))

            // Input Correo
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "CORREO ELECTRÓNICO",
                    fontSize = 11.sp,
                    color = colorTextoGris,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("nombre@ejemplo.com", color = Color.LightGray) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Contraseña
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CONTRASEÑA",
                        fontSize = 11.sp,
                        color = colorTextoGris,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "¿Olvidaste la clave?",
                        fontSize = 12.sp,
                        color = colorPrimario,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("........", color = Color.LightGray) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botón Iniciar Sesión
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
                shape = RoundedCornerShape(50)
            ) {
                Text("Iniciar Sesión", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Divisor "o continúa con"
            Text(text = "o continúa con", fontSize = 13.sp, color = colorTextoGris)

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de Redes Sociales
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIconButton(text = "G", textColor = Color(0xFF4285F4)) {}
                SocialIconButton(text = "f", textColor = Color(0xFF1877F2)) {}
                SocialIconButton(text = "", textColor = Color.Black) {}
            }

            // Espaciador para empujar el botón al fondo
            Spacer(modifier = Modifier.weight(1f))

            // Botón Registrarse (Abre RegisterScreen)
            OutlinedButton(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = colorPrimario),
                border = BorderStroke(1.dp, colorPrimario)
            ) {
                Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Text(
                text = "Al entrar, aceptas nuestros Términos y Privacidad",
                fontSize = 11.sp,
                color = colorTextoGris,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun SocialIconButton(
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() },
        shape = CircleShape,
        color = Color.White.copy(alpha = 0.8f),
        border = BorderStroke(1.dp, Color.White),
        shadowElevation = 2.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}