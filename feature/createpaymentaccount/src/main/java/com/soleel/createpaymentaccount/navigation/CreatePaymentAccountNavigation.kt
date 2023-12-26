package com.soleel.createpaymentaccount.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.createpaymentaccount.CreatePaymentAccountRoute


const val createPaymentAccountRoute = "create_payment_account_route"

fun NavController.navigateToCreatePaymentAccount(navOptions: NavOptions? = null) {
    this.navigate(createPaymentAccountRoute, navOptions)
}

fun NavGraphBuilder.createPaymentAccountScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = createPaymentAccountRoute,
        content = { CreatePaymentAccountRoute(onBackClick = onBackClick) }
    )
}