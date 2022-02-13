package com.manicpixie.cfttest.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.manicpixie.cfttest.R




val montserratFont = FontFamily(
    listOf(
        Font(R.font.montserrat_regular, FontWeight.Medium),
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold)
    )
)

val robotoFont = FontFamily(
    listOf(
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_regular, FontWeight.Medium)
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    caption = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Bold,
        color = Color.White

    ),
    body1 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Medium,
        color = Color.White
    ),
    body2 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Normal
    ),
    h1 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.04.em,
        color = Color.White
    ),
    h2 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold
    ),
    h3 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.04.em,
        color = Color.Black
    ),
    h4 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
    ),
    h5 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    ),
    h6 = TextStyle(
        fontFamily = montserratFont,
        fontWeight = FontWeight.SemiBold,
        color = DarkGray,
        letterSpacing = 0.045.em
    ),
)