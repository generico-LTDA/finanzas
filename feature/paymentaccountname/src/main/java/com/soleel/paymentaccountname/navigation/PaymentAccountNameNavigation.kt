package com.soleel.paymentaccountname.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.paymentaccountname.PaymentAccountNameRoute


const val paymentAccountNameRoute = "payment_account_name_route";

fun NavController.navigateToPaymentAccountNameRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountNameRoute, navOptions)
}

fun NavGraphBuilder.paymentAccountNameScreen() {
    composable(
        route = paymentAccountNameRoute,
        content = {
            PaymentAccountNameRoute()
        }
    )
}