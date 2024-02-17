package com.soleel.paymentaccountcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation


const val createPaymentAccountGraph = "create_payment_account_graph"
const val createPaymentAccountRoute = "create_payment_account_route"

fun NavController.navigateToPaymentAccountCreateGraph(navOptions: NavOptions? = null) {
    this.navigate(createPaymentAccountGraph, navOptions)
}

fun NavGraphBuilder.paymentAccountCreateGraph(
    startDestination: String,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination,
        route = createPaymentAccountGraph,
        builder = {
            nestedGraphs()
        }
    )
}