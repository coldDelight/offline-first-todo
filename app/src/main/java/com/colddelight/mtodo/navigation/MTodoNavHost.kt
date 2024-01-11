package com.colddelight.mtodo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.colddelight.daily.navigation.DailyRoute
import com.colddelight.mtodo.ui.MTodoAppState
import com.colddelight.daily.navigation.dailyScreen
import com.colddelight.history.navigation.historyScreen
import com.colddelight.madalart.navigation.mandalArtScreen

@Composable
fun MtodoNavHost(
    appState: MTodoAppState,
    modifier: Modifier = Modifier,
    startDestination: String = DailyRoute.route
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        dailyScreen()
        historyScreen()
        mandalArtScreen()
    }
}