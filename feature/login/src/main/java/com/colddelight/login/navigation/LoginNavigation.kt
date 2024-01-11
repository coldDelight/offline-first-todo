package com.colddelight.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.login.LoginScreen

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(LoginRoute.route, navOptions)
}

fun NavGraphBuilder.loginScreen(
) {
    composable(route = LoginRoute.route) {
        LoginScreen()
    }
}

object LoginRoute {
    const val route: String = "login"
}