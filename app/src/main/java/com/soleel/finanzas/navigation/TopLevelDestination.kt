package com.soleel.finanzas.navigation

import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
//    val selectedIcon: ImageVector,
//    val unselectedIcon: ImageVector,
    val iconTextId: String,
    val titleTextId: String,
) {
    HOME(
        iconTextId = "Home",
        titleTextId = "Home",
    )
}
