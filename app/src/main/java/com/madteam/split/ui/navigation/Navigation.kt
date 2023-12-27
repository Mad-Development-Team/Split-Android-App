package com.madteam.split.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.madteam.split.ui.screens.welcome.ui.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.WelcomeScreen.route
    ) {
        composable(Screens.WelcomeScreen.route) {
            WelcomeScreen()
        }
    }
}