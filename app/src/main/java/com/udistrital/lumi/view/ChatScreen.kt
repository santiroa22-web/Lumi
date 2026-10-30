package com.udistrital.lumi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.lumi.R
import com.udistrital.lumi.data.MensajeChat
import com.udistrital.lumi.ui.theme.*
import com.udistrital.lumi.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    onBackClick: () -> Unit = {},
    viewModel: ChatViewModel = viewModel()
) {
    val estadoLista = rememberLazyListState()

    // Cada vez que se agrega un mensaje desplaza automáticamente al final
    LaunchedEffect(viewModel.mensajes.size) {
        if (viewModel.mensajes.isNotEmpty()) {
            estadoLista.animateScrollToItem(viewModel.mensajes.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondo)
            .imePadding()
    ) {
        // Barra superior
        Surface(color = Color.White, shadowElevation = 1.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Image(
                    painter = painterResource(id = R.drawable.lumi),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Lumi", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextoOscuro)
                    Text("espacio seguro", fontSize = 12.sp, color = VerdeLumi)
                }
                IconButton(onClick = { }) { Icon(Icons.Outlined.Info, contentDescription = "Información") }
                IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, contentDescription = "Más opciones") }
            }
        }

        // Historial de chat
        LazyColumn(
            state = estadoLista,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("HOY", fontSize = 11.sp, color = TextoGris)
                }
            }
            items(viewModel.mensajes) { mensaje ->
                BurbujaMensaje(mensaje)
            }
        }

        // Campo para escribir
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.textoNuevo,
                onValueChange = { viewModel.onTextoChange(it) },
                placeholder = { Text("Escribe un mensaje a Lumi", color = Color.LightGray) },
                leadingIcon = { Icon(Icons.Outlined.EmojiEmotions, contentDescription = null, tint = TextoGris) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = GrisFondo,
                    unfocusedContainerColor = GrisFondo,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 3
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilledIconButton(
                onClick = { viewModel.enviarMensaje() },
                modifier = Modifier.size(52.dp),
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = VerdeLumi)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar", tint = Color.White)
            }
        }
    }
}

@Composable
fun BurbujaMensaje(mensaje: MensajeChat) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (mensaje.esDeLumi) Arrangement.Start else Arrangement.End
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomStart = if (mensaje.esDeLumi) 4.dp else 20.dp,
                bottomEnd = if (mensaje.esDeLumi) 20.dp else 4.dp
            ),
            color = if (mensaje.esDeLumi) VerdeSuave else Color.White,
            shadowElevation = if (mensaje.esDeLumi) 0.dp else 1.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(mensaje.texto, fontSize = 15.sp, color = TextoOscuro, lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(mensaje.hora, fontSize = 10.sp, color = TextoGris)
            }
        }
    }
}