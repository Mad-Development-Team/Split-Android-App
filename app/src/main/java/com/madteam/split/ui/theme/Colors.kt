package com.madteam.split.ui.theme

import androidx.compose.ui.graphics.Color

// Split Colors App

// Base colors

// Base
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

// Neutrals
val Grey900 = Color(0xFF333333)
val Grey800 = Color(0xFF666666)
val Grey600 = Color(0xFF999999)
val Grey500 = Color(0xFFCCCCCC)
val Grey400 = Color(0xFFDDDDDD)
val Grey300 = Color(0xFFE9E9E9)
val Grey200 = Color(0xFFF2F2F2)
val Grey100 = Color(0xFFF7F7F7)

// Primary
val Blue900 = Color(0xFF0077B6)
val Blue500 = Color(0xFF00B4D8)
val Blue300 = Color(0xFFCAF0F8)

// Secondary
val Magnolia900 = Color(0xFFE6E0E9)
val Magnolia500 = Color(0xFFC4C5D4)
val Magnolia300 = Color(0xFF9DADBF)

// Red
val Red900 = Color(0xFF480000)
val Red700 = Color(0xFFCB0003)
val Red300 = Color(0xFFFFD8D5)

// Green
val Green900 = Color(0xFF388E3C)
val Green700 = Color(0xFF66BB6A)
val Green300 = Color(0xFFF2FDF4)

data class SplitColors(
    val neutral: Neutral = Neutral(),
    val primary: Primary = Primary(),
    val secondary: Secondary = Secondary(),
    val error: Error = Error(),
    val success: Success = Success()
)

val splitColorsDark = SplitColors(
    neutral = Neutral(
        textTitle = White,
        textHeavy = Grey200,
        textBody = Grey200,
        textStrong = Grey300,
        textMedium = Grey600,
        textExtraWeak = Black,
        textDisabled = Grey500,
        textLinkDefault = Blue500,
        textLinkHover = Blue900,
        textLinkDisabled = Grey400,
        textLinkExtraWeak = Black,
        iconHeavy = White,
        iconExtraWeak = Black,
        borderDefault = Grey600,
        borderMedium = Grey400,
        borderStrong = White,
        borderDisabled = Grey800,
        borderExtraWeak = Black,
        backgroundHeavy = White,
        backgroundDark = Grey400,
        backgroundMedium = Grey900,
        backgroundExtraWeak = Black
    )
)

data class Neutral(
    val textTitle: Color = Black,
    val textHeavy: Color = Grey900,
    val textBody: Color = Grey900,
    val textStrong: Color = Grey800,
    val textMedium: Color = Grey600,
    val textExtraWeak: Color = White,
    val textDisabled: Color = Grey500,
    val textLinkDefault: Color = Blue500,
    val textLinkHover: Color = Blue900,
    val textLinkDisabled: Color = Grey400,
    val textLinkExtraWeak: Color = White,
    val iconHeavy: Color = Black,
    val iconExtraWeak: Color = White,
    val borderDefault: Color = Grey300,
    val borderMedium: Color = Grey600,
    val borderStrong: Color = Black,
    val borderDisabled: Color = Grey400,
    val borderExtraWeak: Color = White,
    val backgroundHeavy: Color = Black,
    val backgroundDark: Color = Grey900,
    val backgroundMedium: Color = Grey400,
    val backgroundExtraWeak: Color = White
)

data class Primary(
    val iconDefault: Color = Blue500,
    val backgroundStrong: Color = Blue900,
    val backgroundMedium: Color = Blue500,
    val backgroundWeak: Color = Blue300
)

data class Secondary(
    val backgroundMedium: Color = Magnolia900
)

data class Error(
    val textDefault: Color = Red700,
    val iconDefault: Color = Red700,
    val borderDefault: Color = Red900,
    val backgroundDefault: Color = Red300
)

data class Success(
    val textDefault: Color = Green900,
    val iconDefault: Color = Green900,
    val borderDefault: Color = Green900,
    val backgroundDefault: Color = Green300
)
