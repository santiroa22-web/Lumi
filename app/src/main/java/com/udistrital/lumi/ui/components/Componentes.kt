package com.udistrital.lumi.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import com.udistrital.lumi.ui.theme.*

// Componentes que se repetian en varias pantallas.
// Antes el TextField estaba copiado 5 veces y ademas tenia value = "" fijo,
// por eso no se podia escribir nada en el login ni en el registro.

@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    placeholder: String,
    icono: ImageVector,
    esContrasena: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    accionDerecha: (@Composable () -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = etiqueta,
                fontSize = 11.sp,
                color = TextoGris,
                fontWeight = FontWeight.SemiBold
            )
            if (accionDerecha != null) {
                accionDerecha()
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        TextField(
            value = valor,
            onValueChange = onValorChange,
            placeholder = { Text(placeholder, color = Color.LightGray) },
            leadingIcon = { Icon(icono, contentDescription = null, tint = Color.Gray) },
            // La contraseña ahora sale con puntos
            visualTransformation = if (esContrasena) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esContrasena) KeyboardType.Password else tipoTeclado
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.6f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = VerdeLumi
            ),
            singleLine = true
        )
    }
}

@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true,
    mostrarFlecha: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = VerdeLumi,
            contentColor = Color.White,
            disabledContainerColor = GrisBoton,
            disabledContentColor = Color(0xFF9E9E9E)
        ),
        shape = RoundedCornerShape(50)
    ) {
        Text(texto, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        if (mostrarFlecha) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Chip naranja/verde con la racha de dias
@Composable
fun ChipRacha(dias: Int) {
    Surface(
        shape = RoundedCornerShape(50),
        color = VerdeClaro,
        border = BorderStroke(1.dp, VerdeSuave)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = NaranjaRacha,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("$dias días", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = VerdeLumi)
        }
    }
}

// Devuelve la carita segun el estado de animo (0 a 4)
fun iconoAnimo(animo: Int): ImageVector {
    return when (animo) {
        0 -> Icons.Default.SentimentVeryDissatisfied
        1 -> Icons.Default.SentimentDissatisfied
        2 -> Icons.Default.SentimentNeutral
        3 -> Icons.Default.SentimentSatisfied
        4 -> Icons.Default.SentimentVerySatisfied
        else -> Icons.Outlined.Circle
    }
}

// Fila de las 5 caritas (se usa en el Home)
@Composable
fun SelectorAnimo(
    seleccionado: Int?,
    onSeleccion: (Int) -> Unit
) {
    val etiquetas = listOf("MAL", "BAJO", "NEUTRAL", "BIEN", "GENIAL")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0..4) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { onSeleccion(i) },
                    shape = CircleShape,
                    color = if (seleccionado == i) VerdeLumi else Color.White,
                    shadowElevation = 1.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            iconoAnimo(i),
                            contentDescription = etiquetas[i],
                            tint = if (seleccionado == i) Color.White else TextoGris,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(etiquetas[i], fontSize = 10.sp, color = TextoGris)
            }
        }
    }
}

// Encabezado de los 3 pasos del registro diario: X, racha y barra de progreso
@Composable
fun EncabezadoCheckIn(
    paso: Int,
    totalPasos: Int = 3,
    titulo: String? = null,
    onCerrar: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onCerrar) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = TextoOscuro)
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                if (titulo != null) {
                    Text(titulo, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
                }
            }
            ChipRacha(dias = com.udistrital.lumi.data.DatosDePrueba.DIAS_RACHA)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("PASO $paso DE $totalPasos", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = VerdeLumi)
            Text("${paso * 100 / totalPasos}%", fontSize = 12.sp, color = TextoGris)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { paso.toFloat() / totalPasos },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = VerdeLumi,
            trackColor = GrisBoton
        )
    }
}

// Barra de progreso con icono, usada en la pantalla de la mascota
@Composable
fun BarraEstadistica(
    nombre: String,
    valor: Int,
    icono: ImageVector,
    colorIcono: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = CircleShape, color = colorIcono.copy(alpha = 0.15f), modifier = Modifier.size(28.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(nombre, fontSize = 14.sp, color = TextoOscuro, modifier = Modifier.weight(1f))
            Text("$valor%", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextoOscuro)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { valor / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = VerdeLumi,
            trackColor = GrisBoton
        )
    }
}
