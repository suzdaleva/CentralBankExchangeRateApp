package com.manicpixie.cfttest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BackgroundBlue,
    primaryVariant = DarkestNavy,
    secondary = Blue,
    background = BackBlack,
    surface = SurfaceGray,
    onPrimary = Color(0xFF7D7DBC),
    onSecondary = Color(0xFF545481),
    onBackground = Green,
    onSurface = Color.White

)

private val LightColorPalette = lightColors(
    primary = BackgroundBlue,
    primaryVariant = DarkestNavy,
    secondary = Blue,
    background = BackLightGray,
    surface = SurfaceWhite,
    onPrimary = Color.LightGray.copy(0.2f),
    onSecondary = Color.LightGray.copy(0.9f),
    onBackground = Color.White,
    onSurface = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CFTTestTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }



    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}