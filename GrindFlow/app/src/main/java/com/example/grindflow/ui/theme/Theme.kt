package com.example.grindflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8E44AD),
    secondary = Color(0xFFE83E8C),
    tertiary = Color(0xFF7B2CBF),

    background = Color(0xFFF8F5FC),
    surface = Color.White,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = Color(0xFF333333),
    onSurface = Color(0xFF333333)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFFFF80AB),
    tertiary = Color(0xFF9C64D6),

    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),

    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.White,

    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun GrindFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}