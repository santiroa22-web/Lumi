package com.udistrital.lumi.views.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {}
) {
    // Captura el gesto/botón de atrás del sistema Android
    BackHandler {
        onBackClick()
    }

    var isChecked by remember { mutableStateOf(true) }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEBF4FF),
            Color(0xFFFDE8E8),
            Color(0xFFE4E5F1)
        )
    )

    val colorPrimario = Color(0xFF60C5B1)
    val colorTextoOscuro = Color(0xFF1A1A1A)
    val colorTextoGris = Color(0xFF757575)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Botón Regresar superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = colorTextoOscuro
                    )
                }
            }

            Spacer(modifier = Modifier.height(76.dp))

            // Título y Subtítulo
            Text(
                text = "Comienza tu viaje",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorTextoOscuro
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Únete a Lumi para cultivar tu paz mental.",
                fontSize = 14.sp,
                color = colorTextoGris,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Input Nombre
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "NOMBRE",
                    fontSize = 11.sp,
                    color = colorTextoGris,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Tu nombre completo", color = Color.LightGray) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
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

            Spacer(modifier = Modifier.height(14.dp))

            // Input Correo
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "CORREO",
                    fontSize = 11.sp,
                    color = colorTextoGris,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("ejemplo@correo.com", color = Color.LightGray) },
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

            Spacer(modifier = Modifier.height(14.dp))

            // Input Contraseña
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "CONTRASEÑA",
                    fontSize = 11.sp,
                    color = colorTextoGris,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Tu contraseña", color = Color.LightGray) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox Términos
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = colorTextoGris
                    )
                )

                val termsText = buildAnnotatedString {
                    append("Acepto los ")
                    withStyle(style = SpanStyle(color = colorPrimario, fontWeight = FontWeight.Bold)) {
                        append("Términos y Condiciones")
                    }
                    append(" y la ")
                    withStyle(style = SpanStyle(color = colorPrimario, fontWeight = FontWeight.Bold)) {
                        append("Política de Privacidad")
                    }
                    append(" de Lumi.")
                }

                Text(
                    text = termsText,
                    fontSize = 12.sp,
                    color = colorTextoGris,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Crear Cuenta
            Button(
                onClick = onCreateAccountClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorPrimario),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Crear Cuenta",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Enlace a Iniciar Sesión
            val loginText = buildAnnotatedString {
                append("¿Ya tienes una cuenta? ")
                withStyle(style = SpanStyle(color = colorPrimario, fontWeight = FontWeight.Bold)) {
                    append("Inicia sesión")
                }
            }

            Text(
                text = loginText,
                fontSize = 13.sp,
                color = colorTextoGris,
                modifier = Modifier
                    .clickable { onLoginClick() }
                    .padding(bottom = 24.dp)
            )
        }
    }
}