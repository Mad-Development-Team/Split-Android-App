package com.madteam.split.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSplitColors = staticCompositionLocalOf { SplitColors() }
val LocalSplitColorsDark = staticCompositionLocalOf { splitColorsDark }
val LocalSplitTypography = staticCompositionLocalOf { SplitTypes() }

@Composable
fun SplitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val splitColorsLight = SplitColors()
    val splitColorsDark = splitColorsDark
    val splitTypography = SplitTypes()

    if (darkTheme) {
        CompositionLocalProvider {
            LocalSplitColorsDark.provides(splitColorsDark)
            LocalSplitTypography.provides(splitTypography)
            content()
        }
    } else {
        CompositionLocalProvider {
            LocalSplitColors.provides(splitColorsLight)
            LocalSplitTypography.provides(splitTypography)
            content()
        }
    }
}

object SplitTheme {
    val colors: SplitColors
        @Composable
        get() = if (isSystemInDarkTheme()) {
            LocalSplitColorsDark.current
        } else {
            LocalSplitColors.current
        }

    val typography: SplitTypes
        @Composable
        get() = LocalSplitTypography.current
}