package com.madteam.split.utils

import androidx.navigation.NavController

fun NavController.navigateWithPopUpTo(
    route: String,
    inclusive: Boolean = false,
    popUpTo: String
) {
    navigate(route) {
        popUpTo(popUpTo) {
            this.inclusive = inclusive
        }
    }
}