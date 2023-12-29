package com.example.authapp.core.utils

sealed class Screen(val route: String) {
    object LoginScreen: Screen("main_screen")
    object ConfirmCodeScreen: Screen("confirm_code_screen")
}