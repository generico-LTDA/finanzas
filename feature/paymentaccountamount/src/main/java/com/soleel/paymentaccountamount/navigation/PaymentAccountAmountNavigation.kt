package com.soleel.paymentaccountamount.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.soleel.paymentaccountamount.PaymentAccountAmountRoute


const val paymentAccountAmountRoute = "enter_payment_account_amount_route";

fun NavController.navigateToPaymentAccountAmountRoute(navOptions: NavOptions? = null) {
    this.navigate(paymentAccountAmountRoute, navOptions)
}

fun NavGraphBuilder.paymentAccountAmountScreen(
    onShowBottomBar: () -> Unit,
    onShowAddFloating: () -> Unit,
    onCancelClick: () -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        route = paymentAccountAmountRoute,
        content = {
            PaymentAccountAmountRoute(
                onShowBottomBar = onShowBottomBar,
                onShowAddFloating = onShowAddFloating,
                onCancelClick = onCancelClick,
                onBackClick = onBackClick
            )
        }
    )
}