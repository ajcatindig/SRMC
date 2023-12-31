package com.example.srmc.composeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
        primary = primary,
        primaryVariant = primary,
        background = backgroundNight,
        surface = surfaceNight,
        onBackground = white,
        onPrimary = white
                                         )

private val LightColorPalette = lightColors(
        primary = primary,
        primaryVariant = primary,
        background = backgroundDay,
        surface = surfaceDay,
        onBackground = black,
        onPrimary = primary
                                           )

@Composable
fun SRMCTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
             ) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
                 )
}
