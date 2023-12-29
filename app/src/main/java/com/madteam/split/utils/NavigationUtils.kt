package com.madteam.split.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

fun NavController.navigateWithPopUpTo(
    route: String,
    inclusive: Boolean = false
) {
    navigate(route) {
        popUpTo(route) {
            this.inclusive = inclusive
        }
    }
}