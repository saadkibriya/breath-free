package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PixelMint,
    onPrimary = Color(0xFF00281A),
    primaryContainer = PixelMintContainer,
    onPrimaryContainer = OnPixelMintContainer,
    secondary = PixelPink,
    onSecondary = Color(0xFF370014),
    secondaryContainer = PixelPinkContainer,
    onSecondaryContainer = OnPixelPinkContainer,
    tertiary = PixelSunYellow,
    onTertiary = Color(0xFF332000),
    tertiaryContainer = GoldContainer,
    onTertiaryContainer = OnGoldContainer,
    error = PixelPinkVibrant,
    onError = Color.White,
    errorContainer = PixelPinkContainer,
    onErrorContainer = OnPixelPinkContainer,
    background = PixelDarkBackground,
    onBackground = PixelDarkTextPrimary,
    surface = PixelDarkSurface,
    onSurface = PixelDarkTextPrimary,
    surfaceVariant = PixelDarkSurfaceVariant,
    onSurfaceVariant = PixelDarkTextSecondary,
    outline = PixelDarkCardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = PixelMintDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD1FAE5),
    onPrimaryContainer = Color(0xFF064E3B),
    secondary = PixelPinkVibrant,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE4E6),
    onSecondaryContainer = Color(0xFF881337),
    tertiary = Color(0xFFB45309),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFEF3C7),
    onTertiaryContainer = Color(0xFF78350F),
    error = PixelPinkVibrant,
    onError = Color.White,
    errorContainer = Color(0xFFFFE4E6),
    onErrorContainer = Color(0xFF9F1239),
    background = PixelLightBackground,
    onBackground = PixelLightTextPrimary,
    surface = PixelLightSurface,
    onSurface = PixelLightTextPrimary,
    surfaceVariant = PixelLightSurfaceVariant,
    onSurfaceVariant = PixelLightTextSecondary,
    outline = PixelLightBorder
)

@Composable
fun BreatheFreeTheme(
    darkTheme: Boolean = true, // Signature Pixel dark mode with vibrant mint & playful pink accents
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
