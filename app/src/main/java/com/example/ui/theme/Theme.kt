package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Teal400,
    onPrimary = Navy950,
    primaryContainer = Teal600,
    onPrimaryContainer = Teal50,
    secondary = Amber400,
    onSecondary = Navy950,
    secondaryContainer = Amber600,
    onSecondaryContainer = Amber50,
    tertiary = Emerald400,
    onTertiary = Navy950,
    tertiaryContainer = Emerald600,
    onTertiaryContainer = Emerald100,
    background = Navy950,
    onBackground = Neutral100,
    surface = Navy900,
    onSurface = Neutral100,
    surfaceVariant = Navy850,
    onSurfaceVariant = Neutral300,
    outline = Navy700,
    outlineVariant = Navy800
)

private val LightColorScheme = lightColorScheme(
    primary = Teal600,
    onPrimary = PureWhite,
    primaryContainer = Teal100,
    onPrimaryContainer = Teal600,
    secondary = Amber600,
    onSecondary = PureWhite,
    secondaryContainer = Amber100,
    onSecondaryContainer = Amber600,
    tertiary = Emerald600,
    onTertiary = PureWhite,
    tertiaryContainer = Emerald100,
    onTertiaryContainer = Emerald600,
    background = Neutral50,
    onBackground = Neutral900,
    surface = PureWhite,
    onSurface = Neutral900,
    surfaceVariant = Neutral100,
    onSurfaceVariant = Neutral600,
    outline = Neutral300,
    outlineVariant = Neutral200
)

@Composable
fun VitaVueTheme(
    darkTheme: Boolean = true, // Default to deep midnight theme for high-tech health feel
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
