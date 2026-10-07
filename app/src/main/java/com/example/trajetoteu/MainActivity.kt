package com.example.trajetoteu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trajetoteu.ui.navigation.AppNavigation
import com.example.trajetoteu.ui.theme.TrajetoTeuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrajetoTeuTheme {
                AppNavigation()
            }
        }
    }
}
