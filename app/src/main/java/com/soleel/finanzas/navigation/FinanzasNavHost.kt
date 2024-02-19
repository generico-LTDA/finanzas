package com.soleel.finanzas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.soleel.accounts.navigation.accountsScreen
import com.soleel.createtransaction.navigation.createTransactionScreen
import com.soleel.finanzas.ui.FinanzasAppState
import com.soleel.home.navigation.homeRoute
import com.soleel.home.navigation.homeScreen
import com.soleel.paymentaccountcreate.navigation.navigateToPaymentAccountAmountRoute
import com.soleel.paymentaccountcreate.navigation.navigateToPaymentAccountNameRoute
import com.soleel.paymentaccountcreate.navigation.paymentAccountCreateGraph
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

            paymentAccountCreateGraph(
                navController = navController,
                onShowBottomBar = appState::showBottomBar,
                onShowAddFloating = appState::showAddFloating,
                onCancelClick = appState::showCancelAlert,
                onBackClick = navController::popBackStack,
                fromTypeToName = navController::navigateToPaymentAccountNameRoute,
                fromNameToAmount = navController::navigateToPaymentAccountAmountRoute
            )

            createTransactionScreen(
                onShowBottomBar = appState::showBottomBar,
                onShowAddFloating = appState::showAddFloating,
                onBackClick = navController::popBackStack,
                onCancelClick = appState::showCancelAlert,
            )
        }
    )
}

//fun NavGraphBuilder.finanzas(navController: NavHostController): NavGraphBuilder {
//    homeScreen()
//
//    statsScreen()
//
//    accountsScreen()
//
//    profileScreen()
//
//    createPaymentAccountGraph(
//        onShowBottomBar = appState::showBottomBar,
//        onShowAddFloating = appState::showAddFloating,
//        onBackClick = navController::popBackStack,
//        onCancelClick = appState::showCancelAlert,
//        nestedGraphs = {
//            selectTypePaymentAccountScreen()
//            enterTransactionNameScreen()
//            enterTransactionAmountScreen()
//        }
//    )
//
//    createTransactionScreen(
//        onShowBottomBar = appState::showBottomBar,
//        onShowAddFloating = appState::showAddFloating,
//        onBackClick = navController::popBackStack,
//        onCancelClick = appState::showCancelAlert,
//    )
//}