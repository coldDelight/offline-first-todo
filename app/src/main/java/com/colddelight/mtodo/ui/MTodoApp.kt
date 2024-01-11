package com.colddelight.mtodo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.colddelight.daily.navigation.DailyRoute
import com.colddelight.designsystem.component.MTodoNavigationBar
import com.colddelight.designsystem.component.MTodoNavigationBarItem
import com.colddelight.mtodo.navigation.MtodoNavHost
import com.colddelight.mtodo.navigation.TopLevelDestination
import com.colddelight.mtodo.navigation.TopLevelDestination.DAILY
import com.colddelight.mtodo.navigation.TopLevelDestination.HISTORY
import com.colddelight.mtodo.navigation.TopLevelDestination.MANDALART

@Composable
fun MTodoApp(
//    loginHelper: LoginHelper,
    appState: MTodoAppState = rememberMtodoAppState(
//        loginHelper = loginHelper,
        shouldShowBottomBar = true
    ),
) {
    val currentDestination: String = appState.currentDestination?.route ?: DailyRoute.route
    val destination = appState.currentTopLevelDestination

    Scaffold(
        bottomBar = {
            when (destination) {
                DAILY, HISTORY, MANDALART ->
                    MTodoBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination,
                        modifier = Modifier.testTag("MTodoBottomBar"),
                    )

                else -> {}
            }
        }
    ) { padding ->

        Row(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            Column(Modifier.fillMaxSize()) {
                MtodoNavHost(appState = appState)
            }
        }
    }
}

@Composable
private fun MTodoBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MTodoNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MTodoNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(destination.iconText) },
                modifier = Modifier,
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

