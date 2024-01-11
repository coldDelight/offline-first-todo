package com.colddelight.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.theme.Pink80
import com.colddelight.designsystem.theme.Purple40
import com.colddelight.designsystem.theme.Purple80

@Composable
fun RowScope.MTodoNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = StepNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = StepNavigationDefaults.navigationContentColor(),
            selectedTextColor = StepNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = StepNavigationDefaults.navigationContentColor(),
            indicatorColor = StepNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun MTodoNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = StepNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}


object StepNavigationDefaults {
    @Composable
    fun navigationContentColor() = Color.Black

    @Composable
    fun navigationSelectedItemColor() = Purple40

    @Composable
    fun navigationIndicatorColor() = Purple80
}