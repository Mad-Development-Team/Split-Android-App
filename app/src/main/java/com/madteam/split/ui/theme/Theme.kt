package com.madteam.split.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider

val LocalSplitColors = staticCompositionLocalOf { SplitColors() }
val LocalSplitTypography = staticCompositionLocalOf { SplitTypes() }

@Composable
fun SplitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val splitColorsLight = SplitColors()
    val splitColorsDark = SplitColors() //TODO: Change when dark theme is ready to implement
    val splitTypography = SplitTypes()

    if (darkTheme) {
        CompositionLocalProvider {
            LocalSplitColors.provides(splitColorsDark)
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
        get() = LocalSplitColors.current

    val typography: SplitTypes
        @Composable
        get() = LocalSplitTypography.current
}