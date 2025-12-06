package com.example.roomstorage.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MidnightPrimary,
    secondary = MidnightSecondary,
    background = MidnightBackground,
    surface = MidnightSurface,
    onPrimary = MidnightOnPrimary,
    onSurface = MidnightOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalPrimary,
    secondary = RoyalSecondary,
    background = RoyalBackground,
    surface = RoyalSurface,
    onPrimary = RoyalOnPrimary,
    onSurface = RoyalOnSurface
)

@Composable
fun RoomStorageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is removed in favor of custom themes
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}