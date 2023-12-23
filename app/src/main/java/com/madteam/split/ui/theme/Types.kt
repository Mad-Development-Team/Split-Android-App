package com.madteam.split.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.madteam.split.R

private val poppinsSemibold = FontFamily(
    Font(R.font.poppins_semibold)
)

private val poppinsRegular = FontFamily(
    Font(R.font.poppins_regular)
)

data class SplitTypes(
    val display: Display = Display(),
    val heading: Heading = Heading(),
    val body: Body = Body(),
    val textLink: TextLink = TextLink()
)

data class Display(
    val l: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 48.sp
    ),
    val m: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 40.sp
    ),
    val s: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 32.sp
    )
)

data class Heading(
    val l: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 24.sp
    ),
    val m: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 20.sp
    ),
    val s: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 18.sp
    ),
    val xs: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 16.sp
    ),
    val xxs: TextStyle = TextStyle(
        fontFamily = poppinsSemibold,
        fontSize = 14.sp
    )
)

data class Body(
    val xxl: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 20.sp
    ),
    val xl: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 18.sp
    ),
    val l: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 16.sp
    ),
    val m: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 14.sp
    ),
    val s: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 12.sp
    )
)

data class TextLink(
    val l: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 16.sp,
        textDecoration = TextDecoration.Underline
    ),
    val m: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline
    ),
    val s: TextStyle = TextStyle(
        fontFamily = poppinsRegular,
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
    )
)