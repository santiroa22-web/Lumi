package com.udistrital.lumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.udistrital.lumi.ui.navigation.LumiApp
import com.udistrital.lumi.ui.theme.LumiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Ahora se usa el tema de Lumi en vez del MaterialTheme por defecto
            LumiTheme {
                // Toda la navegacion esta en ui/navigation/NavGraph.kt
                LumiApp()
            }
        }
    }
}
