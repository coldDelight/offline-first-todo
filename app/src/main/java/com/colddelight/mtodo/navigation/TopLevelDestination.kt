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
        selectedIcon = Icons.Outlined.Info,
        unselectedIcon = Icons.Filled.Info,
        iconText = "만다라트",
    ),
    DAILY(
        selectedIcon = Icons.Outlined.Edit,
        unselectedIcon = Icons.Filled.Edit,
        iconText = "데일리",
    ),
    HISTORY(
        selectedIcon = Icons.Outlined.DateRange,
        unselectedIcon = Icons.Filled.DateRange,
        iconText = "기록",
    ),

}