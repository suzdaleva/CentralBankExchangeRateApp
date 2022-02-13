package com.manicpixie.cfttest.ui.theme


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val DarkestNavy = Color(0xFF0E0D5B)

val Blue = Color(0xFF07BAF9)


val BackgroundBlue = Color(0xFFF3F6FF)
val Green = Color(0xFF0ACCA6)



val SurfaceWhite = Color(0xFFFFFFFF)
val SurfaceGray = Color(0xFF272740)
val BackLightGray = Color(0xFFF5F7FB)
val BackBlack = Color(0xFF18182A)
val DarkGray = Color(0xFF6F7792)



val backgroundGradient = Brush.verticalGradient(colors = listOf(Color(0xFF9E00FF), Color(0xFF6100FF)))
val fabGradient =  Brush.linearGradient(
    0.0f to Color(0xFFCC00FF),
    0.6f to Color(0xFF00B2FF),
    1.0f to Color(0xFF2C65F9),
    start = Offset(-10.0f, 13.0f),
    end = Offset(7.0f, 150.0f)
)