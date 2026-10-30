package com.udistrital.lumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.udistrital.lumi.views.screens.LoginScreen
import com.udistrital.lumi.views.screens.OnboardingScreen
import com.udistrital.lumi.views.screens.RegisterScreen

enum class Screen {
    LOGIN,
    REGISTER,
    ONBOARDING
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

                    when (currentScreen) {
                        Screen.LOGIN -> {
                            LoginScreen(
                                onRegisterClick = { currentScreen = Screen.REGISTER }
                            )
                        }
                        Screen.REGISTER -> {
                            RegisterScreen(
                                onBackClick = { currentScreen = Screen.LOGIN },
                                onLoginClick = { currentScreen = Screen.LOGIN },
                                onCreateAccountClick = { currentScreen = Screen.ONBOARDING }
                            )
                        }
                        Screen.ONBOARDING -> {
                            OnboardingScreen(
                                onBackClick = { currentScreen = Screen.REGISTER },
                                onFinishOnboarding = {
                                    // Siguiente pantalla principal o home cuando se implemente
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}