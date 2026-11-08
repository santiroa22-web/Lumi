package com.udistrital.lumi.model.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.udistrital.lumi.model.theme.TextoGris
import com.udistrital.lumi.model.theme.VerdeClaro
import com.udistrital.lumi.model.theme.VerdeLumi
import com.udistrital.lumi.view.CalendarioScreen
import com.udistrital.lumi.view.ChatScreen
import com.udistrital.lumi.view.CheckInAnimoScreen
import com.udistrital.lumi.view.HomeScreen
import com.udistrital.lumi.view.LoginScreen
import com.udistrital.lumi.view.MascotaScreen
import com.udistrital.lumi.view.OlvidoContrasenaScreen
import com.udistrital.lumi.view.OnboardingScreen
import com.udistrital.lumi.view.PausaActivaScreen
import com.udistrital.lumi.view.RachaScreen
import com.udistrital.lumi.view.RegisterScreen
import com.udistrital.lumi.view.VinculosScreen
import com.udistrital.lumi.viewmodel.LoginViewModel
import com.udistrital.lumi.viewmodel.RecuperacionViewModel
import com.udistrital.lumi.viewmodel.RegisterViewModel

// Rutas de la app
object Rutas {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val OLVIDO_CONTRASENA = "olvido_contrasena"
    const val ONBOARDING = "onboarding"
    const val INICIO = "inicio"
    const val CALENDARIO = "calendario"
    const val MASCOTA = "mascota"
    const val CHAT = "chat"
    const val CHECKIN_ANIMO = "checkin_animo"
    const val CHECKIN_PAUSA = "checkin_pausa"
    const val CHECKIN_VINCULOS = "checkin_vinculos"
    const val RACHA = "racha"
}

data class ItemBarra(val ruta: String, val titulo: String, val icono: ImageVector)

val itemsBarra = listOf(
    ItemBarra(Rutas.INICIO, "Inicio", Icons.Default.Home),
    ItemBarra(Rutas.CALENDARIO, "Calendario", Icons.Default.CalendarMonth),
    ItemBarra(Rutas.MASCOTA, "Mascota", Icons.Default.FavoriteBorder)
)

@Composable
fun LumiApp() {
    val navController = rememberNavController()
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    val loginViewModel: LoginViewModel = viewModel()

    val mostrarBarra = rutaActual in listOf(Rutas.INICIO, Rutas.CALENDARIO, Rutas.MASCOTA)

    Scaffold(
        bottomBar = {
            if (mostrarBarra) {
                BarraInferior(navController = navController, rutaActual = rutaActual)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.LOGIN,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            composable(Rutas.LOGIN) {
                if (loginViewModel.navegarAInicio) {
                    LaunchedEffect(Unit) {
                        irAInicio(navController)
                        loginViewModel.reiniciarNavegacion()
                    }
                }

                LoginScreen(
                    loginViewModel = loginViewModel,
                    onLoginClick = { correoInput, contrasenaInput ->
                        loginViewModel.iniciarSesion(
                            correo = correoInput,
                            contrasena = contrasenaInput
                        )
                    },
                    onRegisterClick = { navController.navigate(Rutas.REGISTRO) },
                    onForgotPasswordClick = {
                        navController.navigate(Rutas.OLVIDO_CONTRASENA)
                    }
                )
            }
            composable(Rutas.OLVIDO_CONTRASENA) {
                val recuperacionViewModel: RecuperacionViewModel = viewModel()
                OlvidoContrasenaScreen(
                    viewModel = recuperacionViewModel,
                    onBackClick = { navController.popBackStack() },
                    onNavigateNext = {
                        // Vuelve al login al enviar el enlace correctamente
                        navController.popBackStack()
                    }
                )
            }
            composable(Rutas.REGISTRO) {
                val registerViewModel: RegisterViewModel = viewModel()

                if (registerViewModel.navegarAOnboarding) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Rutas.ONBOARDING) {
                            popUpTo(Rutas.LOGIN)
                        }
                        registerViewModel.reiniciarNavegacion()
                    }
                }

                RegisterScreen(
                    onBackClick = { navController.popBackStack() },
                    onLoginClick = { navController.popBackStack() },
                    onCreateAccountClick = { nombre, correo, contrasena, aceptaTerminos ->
                        registerViewModel.registrarUsuario(
                            nombreInput = nombre,
                            correoInput = correo,
                            contrasenaInput = contrasena,
                            aceptaTerminosInput = aceptaTerminos,
                            onExito = {
                                println("¡Registro de usuario exitoso en Firebase!")
                            },
                            onError = { mensajeError ->
                                println("Error en registro: $mensajeError")
                            }
                        )
                    }
                )
            }
            composable(Rutas.ONBOARDING) {
                OnboardingScreen(
                    onBackClick = { navController.popBackStack() },
                    onFinishOnboarding = { irAInicio(navController) }
                )
            }
            composable(Rutas.INICIO) {
                HomeScreen(
                    onAnimoClick = { navController.navigate(Rutas.CHECKIN_ANIMO) },
                    onAvatarClick = { navController.navigate(Rutas.CHAT) }
                )
            }
            composable(Rutas.CALENDARIO) {
                CalendarioScreen()
            }
            composable(Rutas.MASCOTA) {
                MascotaScreen(onHablarClick = { navController.navigate(Rutas.CHAT) })
            }
            composable(Rutas.CHAT) {
                ChatScreen(onBackClick = { navController.popBackStack() })
            }
            composable(Rutas.CHECKIN_ANIMO) {
                CheckInAnimoScreen(
                    onCerrar = { navController.popBackStack(Rutas.INICIO, inclusive = false) },
                    onContinuar = { navController.navigate(Rutas.CHECKIN_PAUSA) }
                )
            }
            composable(Rutas.CHECKIN_PAUSA) {
                PausaActivaScreen(
                    onCerrar = { navController.popBackStack(Rutas.INICIO, inclusive = false) },
                    onContinuar = { navController.navigate(Rutas.CHECKIN_VINCULOS) }
                )
            }
            composable(Rutas.CHECKIN_VINCULOS) {
                VinculosScreen(
                    onCerrar = { navController.popBackStack(Rutas.INICIO, inclusive = false) },
                    onFinalizar = {
                        navController.navigate(Rutas.RACHA) {
                            popUpTo(Rutas.INICIO)
                        }
                    }
                )
            }
            composable(Rutas.RACHA) {
                RachaScreen(onVolver = {
                    navController.popBackStack(
                        Rutas.INICIO,
                        inclusive = false
                    )
                })
            }
        }
    }
}

private fun irAInicio(navController: NavHostController) {
    navController.navigate(Rutas.INICIO) {
        popUpTo(Rutas.LOGIN) { inclusive = true }
    }
}

@Composable
fun BarraInferior(navController: NavHostController, rutaActual: String?) {
    NavigationBar(containerColor = Color.White) {
        itemsBarra.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = {
                    if (rutaActual != item.ruta) {
                        navController.navigate(item.ruta) {
                            popUpTo(Rutas.INICIO) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icono, contentDescription = item.titulo) },
                label = { Text(item.titulo, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeLumi,
                    selectedTextColor = VerdeLumi,
                    indicatorColor = VerdeClaro,
                    unselectedIconColor = TextoGris,
                    unselectedTextColor = TextoGris
                )
            )
        }
    }
}