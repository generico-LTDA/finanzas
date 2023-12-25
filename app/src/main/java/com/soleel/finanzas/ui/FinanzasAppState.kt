package com.soleel.finanzas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.os.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.soleel.accounts.navigation.navigateToAccounts
import com.soleel.home.navigation.homeRoute
import com.soleel.finanzas.navigation.TopLevelDestination
import com.soleel.home.navigation.navigateToHome
import com.soleel.profile.navigation.navigateToProfile
import com.soleel.stats.navigation.navigateToStats
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberFinanzasAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): FinanzasAppState {
    return remember(
        key1 = navController,
        key2 = coroutineScope,
//        calculation = ::createAppState
        calculation = {
            FinanzasAppState(
                navController = navController,
                coroutineScope = coroutineScope
            )
        }
    )
}

private fun createAppState(
    navController: NavHostController,
    coroutineScope: CoroutineScope
): FinanzasAppState {
    return FinanzasAppState(
        navController = navController,
        coroutineScope = coroutineScope
    )
}

class FinanzasAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {

    @Composable
    fun getCurrentDestination(): NavDestination? {
        return navController.currentBackStackEntryAsState().value?.destination
    }

    @Composable
    fun getCurrentTopLevelDestination(): TopLevelDestination? {
        return when (getCurrentDestination()?.route) {
            homeRoute -> TopLevelDestination.HOME
            else -> null
        }
    }

    fun shouldShowBottomBar(): Boolean {
       return true
    }

    fun topLevelDestinations(): List<TopLevelDestination> {
       return TopLevelDestination.values().asList()
    }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace(
            sectionName = "Navigation: ${topLevelDestination.name}",
            block = {
                val topLevelNavOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(
                        id = navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

                when (topLevelDestination) {
                    TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                    TopLevelDestination.STATS -> navController.navigateToStats(topLevelNavOptions)
                    TopLevelDestination.ACCOUNTS -> navController.navigateToAccounts(topLevelNavOptions)
                    TopLevelDestination.PROFILE -> navController.navigateToProfile(topLevelNavOptions)
                }
            }
        )
    }
}

