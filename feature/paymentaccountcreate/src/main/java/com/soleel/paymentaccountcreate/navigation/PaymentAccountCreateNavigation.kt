package com.soleel.paymentaccountcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.soleel.paymentaccountcreate.CreatePaymentAccountRoute


const val createPaymentAccountGraph = "create_payment_account_graph"
const val createPaymentAccountRoute = "create_payment_account_route"

fun NavController.navigateToCreatePaymentAccountGraph(navOptions: NavOptions? = null) {
    this.navigate(createPaymentAccountGraph, navOptions)
}

fun NavGraphBuilder.paymentAccountCreateGraph(
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = createPaymentAccountGraph,
        startDestination = createPaymentAccountRoute,
        builder = {
            composable(
                route = createPaymentAccountRoute,
                content = {
                    CreatePaymentAccountRoute(
                        onShowBottomBar = onShowBottomBar,
                        onShowAddFloating = onShowAddFloating,
                        onBackClick = onBackClick,
                        onCancelClick = onCancelClick
                    )
                }
            )
            nestedGraphs()
        }
    )
}
