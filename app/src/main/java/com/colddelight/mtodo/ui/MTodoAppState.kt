package com.colddelight.mtodo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.colddelight.daily.navigation.DailyRoute
import com.colddelight.daily.navigation.navigateToDaily
import com.colddelight.history.navigation.HistoryRoute
import com.colddelight.history.navigation.navigateToHistory
import com.colddelight.madalart.navigation.MandalArtRoute
import com.colddelight.madalart.navigation.navigateToMandalArt
import com.colddelight.mtodo.navigation.TopLevelDestination
import com.colddelight.mtodo.navigation.TopLevelDestination.DAILY
import com.colddelight.mtodo.navigation.TopLevelDestination.HISTORY
import com.colddelight.mtodo.navigation.TopLevelDestination.MANDALART

@Composable
fun rememberMtodoAppState(
    navController: NavHostController = rememberNavController(),
): MTodoAppState {
    return remember(navController) {
        MTodoAppState(navController)
    }
}

@Stable
class MTodoAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            DailyRoute.route -> DAILY
            HistoryRoute.route -> HISTORY
            MandalArtRoute.route -> MANDALART
            else -> null
        }
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {

                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                DAILY -> navController.navigateToDaily(topLevelNavOptions)
                HISTORY -> navController.navigateToHistory(topLevelNavOptions)
                MANDALART -> navController.navigateToMandalArt(topLevelNavOptions)
            }
        }
    }
}