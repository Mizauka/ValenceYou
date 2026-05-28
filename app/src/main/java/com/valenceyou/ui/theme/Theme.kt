package com.valenceyou.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF80CBC4),
    secondary = Color(0xFF90CAF9),
    tertiary = Color(0xFFCE93D8),
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF424242),
    onSecondary = Color(0xFF424242),
    onBackground = Color(0xFF424242),
    onSurface = Color(0xFF424242)
)

@Composable
fun ValenceYouTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
