package com.soleel.paymentaccounttype.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.paymentaccounttype.CreateSelectPaymentAccountTypeRoute


const val paymentAccountTypeRoute = "payment_account_type_route";

fun NavController.navigateToPaymentAccountTypeRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountTypeRoute, navOptions)
}

fun NavGraphBuilder.paymentAccountTypeScreen() {
    composable(
        route = paymentAccountTypeRoute,
        content = {
            CreateSelectPaymentAccountTypeRoute()
        }
    )
}