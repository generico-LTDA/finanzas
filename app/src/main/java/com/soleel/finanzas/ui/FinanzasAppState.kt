package com.soleel.finanzas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.soleel.home.navigation.homeRoute
import com.soleel.finanzas.navigation.TopLevelDestination
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
    val coroutineScope: CoroutineScope
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

}

