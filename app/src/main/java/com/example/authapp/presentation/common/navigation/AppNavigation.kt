package com.example.authapp.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.authapp.core.utils.Screen
import com.example.authapp.presentation.confirm_code.ConfirmCodeScreen
import com.example.authapp.presentation.login.LoginScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(Screen.ConfirmCodeScreen.route) {
            ConfirmCodeScreen(navController = navController)
        }
    }

}