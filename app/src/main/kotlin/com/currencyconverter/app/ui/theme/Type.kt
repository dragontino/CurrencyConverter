package com.currencyconverter.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(RobotoFont),
        fontSize = 22.sp,
        lineHeight = 27.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(RobotoFont),
        lineHeight = 22.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(RobotoFont),
        lineHeight = 17.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 26.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 22.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 18.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 24.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.2.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 35.sp,
        lineHeight = 43.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),

    displayLarge = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 50.sp,
        lineHeight = 57.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.2).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 40.sp,
        lineHeight = 47.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.2).sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(VerdanaFont),
        fontSize = 30.sp,
        lineHeight = 37.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    )
)