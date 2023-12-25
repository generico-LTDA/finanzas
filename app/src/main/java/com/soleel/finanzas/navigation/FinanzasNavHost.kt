package com.soleel.finanzas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.soleel.accounts.navigation.accountsScreen
import com.soleel.createpaymentaccount.navigation.createPaymentAccountScreen
import com.soleel.createtransaction.navigation.createTransactionScreen
import com.soleel.home.navigation.homeRoute
import com.soleel.home.navigation.homeScreen
import com.soleel.finanzas.ui.FinanzasAppState
import com.soleel.profile.navigation.profileScreen
import com.soleel.stats.navigation.statsScreen


@Composable
fun FinanzasNavHost(
    appState: FinanzasAppState,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
//        builder = NavGraphBuilder.finanzas(navController)
        builder = {
            homeScreen()

            statsScreen()

            accountsScreen()

            profileScreen()

            createPaymentAccountScreen(
//                onBackClick = navController::popBackStack
                onBackClick = appState::showCancelAlert
            )

            createTransactionScreen(
                onBackClick = navController::popBackStack
            )
        }
    )
}

fun NavGraphBuilder.finanzas(navController: NavHostController) {
    homeScreen()

    statsScreen()

    accountsScreen()

    profileScreen()

    createPaymentAccountScreen(
        onBackClick = navController::popBackStack
    )

    createTransactionScreen(
        onBackClick = navController::popBackStack
    )
}