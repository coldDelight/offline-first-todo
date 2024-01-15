package com.colddelight.mtodo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
) {
    MANDALART(
        unselectedIcon = Icons.Outlined.Info,
        selectedIcon = Icons.Filled.Info,
        iconText = "만다라트",
    ),
    DAILY(
        unselectedIcon = Icons.Outlined.Edit,
        selectedIcon = Icons.Filled.Edit,
        iconText = "데일리",
    ),
    HISTORY(
        unselectedIcon = Icons.Outlined.DateRange,
        selectedIcon = Icons.Filled.DateRange,
        iconText = "기록",
    ),

}