package com.soleel.finanzas.navigation

import com.soleel.ui.R

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: String,
    val titleTextId: String,
) {
    HOME(
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home,
        iconTextId = "Home",
        titleTextId = "Home",
    ),

    STATS(
        selectedIcon = R.drawable.ic_stats,
        unselectedIcon = R.drawable.ic_stats,
        iconTextId = "Stats",
        titleTextId = "Stats",
    ),

    ACCOUNTS(
        selectedIcon = R.drawable.ic_accounts,
        unselectedIcon = R.drawable.ic_accounts,
        iconTextId = "Accounts",
        titleTextId = "Accounts",
    ),

    PROFILE(
        selectedIcon = R.drawable.ic_profile,
        unselectedIcon = R.drawable.ic_profile,
        iconTextId = "Profile",
        titleTextId = "Profile",
    )
}
