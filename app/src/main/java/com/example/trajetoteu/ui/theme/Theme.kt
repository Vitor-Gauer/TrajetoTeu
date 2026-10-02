package com.example.trajetoteu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldGreen,
    secondary = WarmYellow,
    background = DeepNavy,
    surface = SurfaceNavy,
    surfaceVariant = CardBackground,
    onPrimary = Color.White,
    onBackground = OffWhite,
    onSurface = OffWhite,
    onSurfaceVariant = OffWhite
)

@Composable
fun TrajetoTeuTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}