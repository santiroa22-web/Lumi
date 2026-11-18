package com.udistrital.lumi.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.R
import com.udistrital.lumi.model.theme.*
import com.udistrital.lumi.viewmodel.DiarioViewModel
import com.udistrital.lumi.viewmodel.NotaDiario

@Composable
fun DiarioScreen(
    onBackClick: () -> Unit = {},
    viewModel: DiarioViewModel = viewModel()
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Encabezado estilo bloc de notas
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextoOscuro)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Mi Bloc de Gratitud",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Text(
                        text = "Escribe libremente lo que agradeces hoy",
                        fontSize = 12.sp,
                        color = TextoGris
                    )
                }
            }

            Button(
                onClick = {
                    if (viewModel.guardarNota()) {
                        Toast.makeText(context, "Nota guardada en tu diario ✨", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Escribe algo antes de guardar", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeLumi),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Guardar", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Hoja de bloc de notas para escribir
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color(0xFFFFFDF7), // Tono papel cálido suave
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.lumi),
                        contentDescription = "Lumi",
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Espacio seguro • Nueva nota",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VerdeLumi
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de título sin bordes pesados (estilo Apple Notes / Keep)
                TextField(
                    value = viewModel.tituloActual,
                    onValueChange = viewModel::onTituloChange,
                    placeholder = {
                        Text(
                            "Título (opcional)...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoGris.copy(alpha = 0.6f)
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = VerdeSuave,
                        unfocusedIndicatorColor = GrisFondo
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Cuerpo amplio tipo libreta
                TextField(
                    value = viewModel.contenidoActual,
                    onValueChange = viewModel::onContenidoChange,
                    placeholder = {
                        Text(
                            "Hoy me siento agradecido por...\nEscribe aquí tus pensamientos, momentos buenos o cosas que te dieron paz hoy.",
                            fontSize = 15.sp,
                            color = TextoGris.copy(alpha = 0.7f),
                            lineHeight = 22.sp
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        color = TextoOscuro,
                        lineHeight = 22.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 140.dp, max = 210.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Encabezado de notas guardadas
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mis apuntes (${viewModel.notas.size})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
            Icon(Icons.Default.EditNote, contentDescription = null, tint = VerdeLumi, modifier = Modifier.size(20.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Lista de notas guardadas en el bloc
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.notas, key = { it.id }) { nota ->
                TarjetaNotaBloc(
                    nota = nota,
                    onEliminar = { viewModel.eliminarNota(nota) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaNotaBloc(
    nota: NotaDiario,
    onEliminar: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = nota.titulo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Text(
                        text = nota.fecha,
                        fontSize = 11.sp,
                        color = VerdeLumi,
                        fontWeight = FontWeight.Medium
                    )
                }
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.DeleteOutline,
                        contentDescription = "Eliminar nota",
                        tint = TextoGris,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = nota.contenido,
                fontSize = 14.sp,
                color = TextoOscuro.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )
        }
    }
}