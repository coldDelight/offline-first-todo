package com.colddelight.daily.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.daily.DailyScreen

fun NavController.navigateToDaily(navOptions: NavOptions? = null) {
    this.navigate(DailyRoute.route, navOptions)
}

fun NavGraphBuilder.historyScreen(
) {
    composable(route = DailyRoute.route) {
        DailyScreen()
    }
}

object DailyRoute {
    const val route: String = "daily"
}