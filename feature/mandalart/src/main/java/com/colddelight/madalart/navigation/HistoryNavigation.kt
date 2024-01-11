package com.colddelight.madalart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.colddelight.madalart.MandalArtScreen

fun NavController.navigateToMandalArt(navOptions: NavOptions? = null) {
    this.navigate(MandalArtRoute.route, navOptions)
}

fun NavGraphBuilder.mandalArtScreen(
) {
    composable(route = MandalArtRoute.route) {
        MandalArtScreen()
    }
}

object MandalArtRoute {
    const val route: String = "mandalart"
}