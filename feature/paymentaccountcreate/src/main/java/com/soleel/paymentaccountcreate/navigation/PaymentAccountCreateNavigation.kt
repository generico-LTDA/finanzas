package com.soleel.paymentaccountcreate.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.paymentaccountcreate.PaymentAccountCreateViewModel
import com.soleel.paymentaccountcreate.screen.CreateSelectPaymentAccountTypeRoute
import com.soleel.paymentaccountcreate.screen.PaymentAccountAmountRoute
import com.soleel.paymentaccountcreate.screen.PaymentAccountNameRoute


const val paymentAccountCreateGraph = "payment_account_create_graph"

const val paymentAccountTypeRoute = "payment_account_type_route";
const val paymentAccountNameRoute = "payment_account_name_route";
const val paymentAccountAmountRoute = "enter_payment_account_amount_route";

fun NavController.navigateToPaymentAccountCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountCreateGraph, navOptions)
}

fun NavController.navigateToPaymentAccountTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountTypeRoute, navOptions)
}

fun NavController.navigateToPaymentAccountNameRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountNameRoute, navOptions)
}

fun NavController.navigateToPaymentAccountAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountAmountRoute, navOptions)
}

fun NavGraphBuilder.paymentAccountCreateGraph(
    navController: NavHostController,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromTypeToName: () -> Unit,
    fromNameToAmount: () -> Unit,
) {
    navigation(
        startDestination = paymentAccountTypeRoute,
        route = paymentAccountCreateGraph,
        builder = {
            paymentAccountTypeScreen(
                navController = navController,
                onCancelClick = onCancelClick,
                fromTypeToName = fromTypeToName
            )
            paymentAccountNameScreen(
                navController = navController,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount
            )
            paymentAccountAmountScreen(
                navController = navController,
                onShowBottomBar = onShowBottomBar,
                onShowAddFloating = onShowAddFloating,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick
            )
        }
    )
}

fun NavGraphBuilder.paymentAccountTypeScreen(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    fromTypeToName: () -> Unit
) {
    composable(
        route = paymentAccountTypeRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = paymentAccountCreateGraph)
                }
            )

            val viewModel: PaymentAccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            CreateSelectPaymentAccountTypeRoute(
                onCancelClick = onCancelClick,
                fromTypeToName = fromTypeToName,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.paymentAccountNameScreen(
    navController: NavHostController,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit,
    fromNameToAmount: () -> Unit
) {
    composable(
        route = paymentAccountNameRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = paymentAccountCreateGraph)
                }
            )

            val viewModel: PaymentAccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            PaymentAccountNameRoute(
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                fromNameToAmount = fromNameToAmount,
                viewModel = viewModel
            )
        }
    )
}

fun NavGraphBuilder.paymentAccountAmountScreen(
    navController: NavHostController,
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = paymentAccountAmountRoute,
        content = {

            val parentEntry = remember(
                key1 = it,
                calculation = {
                    navController.getBackStackEntry(route = paymentAccountCreateGraph)
                }
            )

            val viewModel: PaymentAccountCreateViewModel = hiltViewModel(
                viewModelStoreOwner = parentEntry
            )

            PaymentAccountAmountRoute(
                onShowBottomBar = onShowBottomBar,
                onShowAddFloating = onShowAddFloating,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick,
                viewModel = viewModel
            )
        }
    )
}