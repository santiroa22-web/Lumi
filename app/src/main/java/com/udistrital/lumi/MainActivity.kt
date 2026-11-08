package com.udistrital.lumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udistrital.lumi.model.theme.LumiTheme
import com.udistrital.lumi.view.CambiarContrasenaScreen
import com.udistrital.lumi.view.LoginScreen
import com.udistrital.lumi.view.OlvidoContrasenaScreen
import com.udistrital.lumi.view.VerificacionCodigoScreen
import com.udistrital.lumi.viewmodel.RecuperacionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LumiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. El controlador que maneja los viajes entre pantallas
                    val navController = rememberNavController()

                    // 2. Instanciamos el ViewModel que van a compartir las 3 pantallas de recuperación
                    val recuperacionViewModel: RecuperacionViewModel = viewModel()

                    // 3. El mapa de navegación
                    NavHost(navController = navController, startDestination = "login") {

                        // Pantalla de Login
                        composable("login") {
                            LoginScreen(
                                onLoginClick = {
                                    // TODO: Navegar al Home cuando el login sea exitoso
                                },
                                onRegisterClick = {
                                    // TODO: Navegar a registro
                                },
                                onForgotPasswordClick = {
                                    // ¡AQUÍ ESTÁ LA MAGIA! Navega a la primera pantalla de olvido
                                    navController.navigate("olvido_contrasena")
                                }
                            )
                        }

                        // Pantalla 1: Ingresar correo
                        composable("olvido_contrasena") {
                            OlvidoContrasenaScreen(
                                viewModel = recuperacionViewModel,
                                onBackClick = { navController.popBackStack() }, // Se devuelve al Login
                                onNavigateNext = { navController.navigate("verificacion_codigo") } // Avanza al código
                            )
                        }

                        // Pantalla 2: Ingresar código de 4 dígitos
                        composable("verificacion_codigo") {
                            VerificacionCodigoScreen(
                                viewModel = recuperacionViewModel,
                                onBackClick = { navController.popBackStack() }, // Se devuelve al correo
                                onNavigateNext = { navController.navigate("cambiar_contrasena") } // Avanza a cambiar clave
                            )
                        }

                        // Pantalla 3: Cambiar contraseña
                        composable("cambiar_contrasena") {
                            CambiarContrasenaScreen(
                                viewModel = recuperacionViewModel,
                                onBackClick = { navController.popBackStack() }, // Se devuelve al código
                                onNavigateHome = {
                                    // Al terminar, lo mandamos al Login y borramos el historial para que no pueda devolverse con la flecha del celular
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}