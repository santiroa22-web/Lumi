package com.udistrital.lumi.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.lumi.ui.components.BotonPrincipal
import com.udistrital.lumi.ui.components.CampoTexto
import com.udistrital.lumi.ui.theme.*

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onCreateAccountClick: () -> Unit = {}
) {
    BackHandler { onBackClick() }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    // Antes venia marcado por defecto; los terminos los debe aceptar el usuario (consentimiento informado)
    var aceptaTerminos by remember { mutableStateOf(false) }

    val formularioValido = nombre.isNotBlank() &&
            correo.contains("@") &&
            contrasena.length >= 6 &&
            aceptaTerminos

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoDegradado)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = TextoOscuro
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(shape = CircleShape, color = VerdeLumi, modifier = Modifier.size(72.dp), shadowElevation = 4.dp) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(38.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Comienza tu viaje",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Únete a Lumi para cultivar tu paz mental.",
                fontSize = 14.sp,
                color = TextoGris,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            CampoTexto(
                etiqueta = "NOMBRE",
                valor = nombre,
                onValorChange = { nombre = it },
                placeholder = "Tu nombre completo",
                icono = Icons.Default.Person
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoTexto(
                etiqueta = "CORREO",
                valor = correo,
                onValorChange = { correo = it },
                placeholder = "ejemplo@correo.com",
                icono = Icons.Default.Email,
                tipoTeclado = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(14.dp))
            CampoTexto(
                etiqueta = "CONTRASEÑA",
                valor = contrasena,
                onValorChange = { contrasena = it },
                placeholder = "Tu contraseña secreta",
                icono = Icons.Default.Lock,
                esContrasena = true
            )
            if (contrasena.isNotEmpty() && contrasena.length < 6) {
                Text(
                    "La contraseña debe tener mínimo 6 caracteres",
                    fontSize = 11.sp,
                    color = RojoSuave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = VerdeLumi,
                        uncheckedColor = TextoGris
                    )
                )
                val textoTerminos = buildAnnotatedString {
                    append("Acepto los ")
                    withStyle(SpanStyle(color = VerdeLumi, fontWeight = FontWeight.Bold)) {
                        append("Términos y Condiciones")
                    }
                    append(" y la ")
                    withStyle(SpanStyle(color = VerdeLumi, fontWeight = FontWeight.Bold)) {
                        append("Política de Privacidad")
                    }
                    append(" de Lumi.")
                }
                Text(text = textoTerminos, fontSize = 12.sp, color = TextoGris, lineHeight = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            BotonPrincipal(
                texto = "Crear Cuenta",
                onClick = onCreateAccountClick,
                habilitado = formularioValido,
                mostrarFlecha = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            val textoLogin = buildAnnotatedString {
                append("¿Ya tienes una cuenta? ")
                withStyle(SpanStyle(color = VerdeLumi, fontWeight = FontWeight.Bold)) {
                    append("Inicia sesión")
                }
            }
            Text(
                text = textoLogin,
                fontSize = 13.sp,
                color = TextoGris,
                modifier = Modifier
                    .clickable { onLoginClick() }
                    .padding(bottom = 24.dp)
            )
        }
    }
}
